package com.simi.shelfpal.ui.updates

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.getExpirySummaryPrompt
import com.simi.shelfpal.data.models.ExpirySummary
import com.simi.shelfpal.data.models.ExpirySummaryItem
import com.simi.shelfpal.data.settings.ShelfPalSettings
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.updates.repository.UpdatesRepository
import com.simi.shelfpal.utils.getExpiryDate
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.datetime.daysUntil
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.ai
import shelfpal.composeapp.generated.resources.info
import shelfpal.composeapp.generated.resources.warning

class UpdatesViewModel : ViewModel(), KoinComponent {

    private val updatesRepository: UpdatesRepository by inject()
    private val firebaseConnector: FirebaseConnector by inject()
    private val shelfPalSettings: ShelfPalSettings by inject()

    private val _recentActivity = MutableStateFlow<List<ShelfPalUpdate>>(emptyList())
    val recentActivity = _recentActivity.asStateFlow()

    private val _aiSummary = MutableStateFlow<ShelfPalUpdate?>(null)
    val aiSummary = _aiSummary.asStateFlow()

    private val _loadingState= MutableStateFlow(LoadingState())
    val loadingState = _loadingState.asStateFlow()

    fun init() {
        viewModelScope.launch {
            _loadingState.value = LoadingState(isLoading = true)
            try {
                with(updatesRepository) {
                    val thisYear = getLocalDateTime().year
                    val nextYear = thisYear + 1

                    getUpdates(thisYear).zip(getUpdates(nextYear)) { thisYearItems, nextYearItems ->
                        thisYearItems + nextYearItems
                    }.collect { items ->
                        val today = items.getUpdate(from = 0, to = 0)
                        val week = items.getUpdate(from = 1, to = 7)
                        val month = items.getUpdate(from = 8, to = 31)
                        val halfAYear = items.getUpdate(from = 32, to = 180)
                        initUpdates(today, week, month, halfAYear)
                        initSummary(today, week, month, halfAYear)
                        _loadingState.value = LoadingState(isLoading = false, isError = false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace() // todo: log to crashlytics
                _loadingState.value = LoadingState(isLoading = false, isError = true)
            }
        }
    }

    private fun List<ShelfPalItem>.toShelfPalUpdate(
        title: String,
        subtitle: String,
        headerIcon: UpdateHeaderIcon,
        updateRecency: UpdateRecency
    ): ShelfPalUpdate {

        return ShelfPalUpdate(
            title = title,
            subtitle = subtitle,
            isNew = hasNewItem(map { it.id }, updateRecency.key),
            items = getItems(),
            headerIcon = headerIcon,
            updateRecency = updateRecency
        )
    }

    private fun List<ShelfPalItem>.getItems(): List<ShelfPalUpdateItem> {
        return this.map {
            ShelfPalUpdateItem(
                id = it.id,
                title = it.basicInfo.name,
                category = it.basicInfo.category
            )
        }
    }

    private fun initUpdates(
        today: List<ShelfPalItem>,
        week: List<ShelfPalItem>,
        month: List<ShelfPalItem>,
        custom: List<ShelfPalItem>,
    ) {
        val todayUpdate = today.toShelfPalUpdate(
            title = "Due Today",
            subtitle = "${today.count()} items expiring today",
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.warning,
                tint = Color.White,
                backgroundColor = Color.Red.copy(alpha = 0.6f)
            ),
            updateRecency = UpdateRecency.TODAY
        )
        val weekUpdate = week.toShelfPalUpdate(
            title = "Due within a Week",
            subtitle = "You have ${week.count()} item(s) expiring within a week",
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.info,
                tint = Color.White,
                backgroundColor = Color.Red.copy(alpha = 0.6f)
            ),
            updateRecency = UpdateRecency.WEEK
        )
        val monthUpdate = month.toShelfPalUpdate(
            title = "Due within a Month",
            subtitle = "You have ${month.count()} items expiring within a month",
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.info,
                tint = Color.White,
                backgroundColor = Color.Green.copy(alpha = 0.6f)
            ),
            updateRecency = UpdateRecency.MONTH
        )
        val customUpdate = custom.toShelfPalUpdate(
            title = "Due in 6 Months",
            subtitle = "You have ${custom.count()} items expiring within 6 months",
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.info,
                tint = Color.White,
                backgroundColor = Color.Gray.copy(alpha = 0.6f)
            ),
            updateRecency = UpdateRecency.CUSTOM
        )

        _recentActivity.value = listOf(
            todayUpdate,
            weekUpdate,
            monthUpdate,
            customUpdate
        ).filter { it.items.orEmpty().isNotEmpty() }
    }

    private fun List<ShelfPalItem>.getUpdate(from: Int, to: Int) =  filter { item ->
        item.expiryInfo.getExpiryDate()?.let {
            getLocalDateTime().date.daysUntil(it) in from..to
        } ?: false
    }

    private fun hasNewItem(newItems: List<Long>, key: String): Boolean {
        val savedItems = shelfPalSettings.getUpdateItemIds(key).orEmpty()
        val hasNew = !savedItems.containsAll(newItems)
        return hasNew
    }


    /**
     *  START REGION: SUMMARY
     */
    private fun List<ShelfPalItem>.getExpirySummary() = map {
        ExpirySummaryItem(
            name = it.basicInfo.name,
            expiryDate = it.expiryInfo.getExpiryDate().toString(),
        )
    }

    private fun createAiSummaryUpdate(summaryResponse: AiExpirySummary) = ShelfPalUpdate(
        title = summaryResponse.title,
        subtitle = summaryResponse.body,
        isNew = null,
        items = null,
        headerIcon = UpdateHeaderIcon(
            icon = Res.drawable.ai,
            tint = primaryLight,
            backgroundColor = primaryContainerLight
        )
    )

    private suspend fun initSummary(
        today: List<ShelfPalItem>,
        week: List<ShelfPalItem>,
        month: List<ShelfPalItem>,
        halfAYear: List<ShelfPalItem>,
    ) {
        val todayExpirySummary = today.getExpirySummary()
        val weekExpirySummary = week.getExpirySummary()
        val monthExpirySummary = month.getExpirySummary()
        val customExpirySummary = halfAYear.getExpirySummary()

        val expirySummary = ExpirySummary(
            expiringInAWeek = weekExpirySummary,
            expiringInAMonth = monthExpirySummary,
            expiringInCustomDuration = customExpirySummary,
            expiringToDay = todayExpirySummary
        )

        val cachedExpirySummary = shelfPalSettings.getExpirySummary()

        if (cachedExpirySummary == null || cachedExpirySummary != expirySummary) {
            try {
                firebaseConnector.getExpirySummary(getExpirySummaryPrompt(expirySummary))?.let {
                    val summaryResponse = Json.decodeFromString<AiExpirySummary>(it)
                    shelfPalSettings.saveExpirySummary(expirySummary)
                    shelfPalSettings.saveAiSummary(summaryResponse)
                    _aiSummary.value = createAiSummaryUpdate(summaryResponse)
                }
            } catch (e: Exception) {
                e.printStackTrace() // todo: log to crashlytics
            }
        } else {
            shelfPalSettings.getAiSummary()?.let {
                _aiSummary.value = createAiSummaryUpdate(it)
            }
        }
    }
    /**
     *  END REGION: SUMMARY
     */
}