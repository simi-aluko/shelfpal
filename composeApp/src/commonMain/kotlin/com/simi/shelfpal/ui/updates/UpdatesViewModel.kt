package com.simi.shelfpal.ui.updates

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.data.database.entities.ExpiryStatus.EXPIRED
import com.simi.shelfpal.data.database.entities.ExpiryStatus.EXPIRING_IN_A_MONTH
import com.simi.shelfpal.data.database.entities.ExpiryStatus.EXPIRING_IN_A_WEEK
import com.simi.shelfpal.data.database.entities.ExpiryStatus.EXPIRING_IN_CUSTOM_DURATION
import com.simi.shelfpal.data.database.entities.ExpiryStatus.EXPIRING_TODAY
import com.simi.shelfpal.data.database.entities.UpdateWithItem
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.getExpirySummaryPrompt
import com.simi.shelfpal.data.models.ExpirySummary
import com.simi.shelfpal.data.models.ExpirySummaryItem
import com.simi.shelfpal.data.settings.ShelfPalSettings
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.updates.repository.UpdatesRepository
import com.simi.shelfpal.utils.getExpiryDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.ai
import shelfpal.composeapp.generated.resources.close
import shelfpal.composeapp.generated.resources.info
import shelfpal.composeapp.generated.resources.warning
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class UpdatesViewModel : ViewModel(), KoinComponent {

    private val updatesRepository: UpdatesRepository by inject()
    private val firebaseConnector: FirebaseConnector by inject()
    private val shelfPalSettings: ShelfPalSettings by inject()

    private val _updates = MutableStateFlow<List<ShelfPalUpdate>>(emptyList())
    val updates = _updates.asStateFlow()

    private val _aiSummary = MutableStateFlow<ShelfPalUpdate?>(null)
    val aiSummary = _aiSummary.asStateFlow()

    private val _loadingState= MutableStateFlow(LoadingState())
    val loadingState = _loadingState.asStateFlow()

    init {
        viewModelScope.launch {
            _loadingState.value = LoadingState(isLoading = true)
            try {
                with(updatesRepository) {
                    val today = getUpdates(EXPIRING_TODAY)
                    val expired = getUpdates(EXPIRED)
                    val week = getUpdates( EXPIRING_IN_A_WEEK)
                    val month = getUpdates(EXPIRING_IN_A_MONTH)
                    val custom = getUpdates(EXPIRING_IN_CUSTOM_DURATION)

                    initSummary(today, week, month, custom)
                    initUpdates(today, week, month, custom, expired)
                }
                _loadingState.value = LoadingState(isLoading = false, isError = false)
            } catch (e: Exception) {
                e.printStackTrace() // todo: log to crashlytics
                _loadingState.value = LoadingState(isLoading = false, isError = true)
            }
        }
    }

    fun List<UpdateWithItem>.toShelfPalUpdate(
        expiryStatus: ExpiryStatus
    ): ShelfPalUpdate {

        val titleSubtitle = getUpdateTitleSubtitle(expiryStatus, this.size)
        val timeStamp = getTimestamp(map { it.expiryUpdate.updatedAt })

        return ShelfPalUpdate(
            title = titleSubtitle.first,
            subtitle = titleSubtitle.second,
            status = expiryStatus,
            timestamp = timeStamp,
            isNew = null,
            items = getItems(),
            headerIcon = expiryStatus.getHeaderIcon()
        )
    }

    private fun getUpdateTitleSubtitle(
        status: ExpiryStatus,
        totalCount: Int
    ): Pair<String, String> {
        return when(status) {
            EXPIRED -> Pair(
                "Expired Items",
                "$totalCount items have recently expired"
            )
            EXPIRING_TODAY -> Pair(
                "Due Today",
                "You have $totalCount items expiring today"
            )
            EXPIRING_IN_A_WEEK -> Pair(
                "Due within a Week",
                "You have $totalCount items expiring within a week"
            )
            EXPIRING_IN_A_MONTH -> Pair(
                "Due within a Month",
                "You have $totalCount items expiring within a month"
            )
            EXPIRING_IN_CUSTOM_DURATION -> Pair(
                "Others",
                "You have set reminders for these items which may be expiring soon"
            )
        }
    }

    private fun getTimestamp(timestamps: List<Long>) =
        timestamps.maxOrNull()?.let { formatToReadableTime(it) }

    private fun List<UpdateWithItem>.getItems(): List<ShelfPalUpdateItem> {
        return this.map {
            ShelfPalUpdateItem(
                id = it.item.id,
                title = it.item.basicInfo.name,
                category = it.item.basicInfo.category,
                onClick = {
                    // todo: navigate to item details
                }
            )
        }
    }

    private fun ExpiryStatus.getHeaderIcon() = when(this) {
        EXPIRED -> UpdateHeaderIcon(
            icon = Res.drawable.close,
            tint = Color.White,
            backgroundColor = Color.Red.copy(alpha = 0.6f)
        )
        EXPIRING_TODAY -> UpdateHeaderIcon(
            icon = Res.drawable.warning,
            tint = Color.White,
            backgroundColor = Color.Red.copy(alpha = 0.6f)
        )
        EXPIRING_IN_A_WEEK -> UpdateHeaderIcon(
            icon = Res.drawable.info,
            tint = Color.White,
            backgroundColor = Color.Red.copy(alpha = 0.6f)
        )
        EXPIRING_IN_A_MONTH -> UpdateHeaderIcon(
            icon = Res.drawable.info,
            tint = Color.White,
            backgroundColor = Color.Green.copy(alpha = 0.6f)
        )
        EXPIRING_IN_CUSTOM_DURATION -> UpdateHeaderIcon(
            icon = Res.drawable.info,
            tint = Color.White,
            backgroundColor = Color.Gray.copy(alpha = 0.6f)
        )
    }

    @OptIn(ExperimentalTime::class)
    private fun formatToReadableTime(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val date = "${dateTime.day} ${dateTime.month.name}, ${dateTime.year}"
        val time = "${dateTime.time.hour}:${dateTime.time.minute}"

        return "$date at $time"
    }

    private fun List<UpdateWithItem>.getExpirySummary() = map {
        ExpirySummaryItem(
            name = it.item.basicInfo.name,
            expiryDate = it.item.expiryInfo.getExpiryDate().toString(),
        )
    }

    private fun createAiSummaryUpdate(summaryResponse: AiExpirySummary) = ShelfPalUpdate(
        title = summaryResponse.title,
        subtitle = summaryResponse.body,
        status = null,
        timestamp = null,
        isNew = null,
        items = null,
        headerIcon = UpdateHeaderIcon(
            icon = Res.drawable.ai,
            tint = primaryLight,
            backgroundColor = primaryContainerLight
        )
    )

    private suspend fun initSummary(
        today: List<UpdateWithItem>,
        week: List<UpdateWithItem>,
        month: List<UpdateWithItem>,
        custom: List<UpdateWithItem>,
    ) {
        val todayExpirySummary = today.getExpirySummary()
        val weekExpirySummary = week.getExpirySummary()
        val monthExpirySummary = month.getExpirySummary()
        val customExpirySummary = custom.getExpirySummary()

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

    private fun initUpdates(
        today: List<UpdateWithItem>,
        week: List<UpdateWithItem>,
        month: List<UpdateWithItem>,
        custom: List<UpdateWithItem>,
        expired: List<UpdateWithItem>,
    ) {
        val todayUpdate = today.toShelfPalUpdate(EXPIRING_TODAY)
        val weekUpdate = week.toShelfPalUpdate(EXPIRING_IN_A_WEEK)
        val monthUpdate = month.toShelfPalUpdate(EXPIRING_IN_A_MONTH)
        val customUpdate = custom.toShelfPalUpdate(EXPIRING_IN_CUSTOM_DURATION)
        val expiredUpdate = expired.toShelfPalUpdate(EXPIRED)

        _updates.value = listOf(
            todayUpdate,
            weekUpdate,
            monthUpdate,
            customUpdate,
            expiredUpdate,
        )
    }
}