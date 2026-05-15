package com.simi.shelfpal.ui.updatedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.settings.ShelfPalSettings
import com.simi.shelfpal.ui.shared.filterExpired
import com.simi.shelfpal.ui.updates.repository.UpdatesRepository
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdatesDetailViewModel(
    val params: UpdatesDetailParams,
) : ViewModel(), KoinComponent {

    private val repository: UpdatesRepository by inject()
    private val shelfPalSettings: ShelfPalSettings by inject()

    private val _state = MutableStateFlow(UpdatesDetailState())
    val state = _state.asStateFlow()

    suspend fun init() {
        when (params.entrySource) {
            UpdateDetailsEntrySource.RECENT_ACTIVITY -> initRecentActivityItems()
            UpdateDetailsEntrySource.EXPIRED_MENU -> initializeExpiredItems()
            UpdateDetailsEntrySource.USED_MENU -> initializeUsedItems()
        }
    }

    fun markAsUsed(itemId: Long) {
        viewModelScope.launch {
            repository.markAsUsed(itemId)
            _state.update { it.copy(items = it.items.filter { item -> item.id != itemId }) }
        }
    }

    fun markAllAsUsed() {
        viewModelScope.launch {
            val ids = _state.value.items.map { it.id }
            repository.markAllAsUsed(ids)
            _state.update { it.copy(items = emptyList()) }
        }
    }

    private fun List<ShelfPalItem>.toUpdateDetailItem() = map {
        UpdatesDetailItem(
            id = it.id,
            name = it.basicInfo.name,
            category = it.basicInfo.category,
        )
    }

    private suspend fun initRecentActivityItems() {
        params.updateRecency?.key?.let {
            shelfPalSettings.saveUpdateItemIds(params.items.toSet(), it)
        }
        repository.getUpdates(params.items).collect {
            _state.value = UpdatesDetailState(
                items = it.toUpdateDetailItem(),
                title = params.title,
                subtitle = params.subtitle,
            )
        }
    }

    private suspend fun initializeExpiredItems() {
        val month = getLocalDateTime().month.number
        val year = getLocalDateTime().year
        repository.getExpiredItems(
            month = month,
            year = year
        ).collect { items ->
            val expiredItems = items.filterExpired()
            _state.value = UpdatesDetailState(
                items = expiredItems.toUpdateDetailItem(),
                title = params.title,
                subtitle = params.subtitle,
            )
        }
    }

    private suspend fun initializeUsedItems() {
        repository.getUsedItems().collect {
            _state.value = UpdatesDetailState(
                items = it.toUpdateDetailItem(),
                title = params.title,
                subtitle = params.subtitle,
            )
        }
    }
}

