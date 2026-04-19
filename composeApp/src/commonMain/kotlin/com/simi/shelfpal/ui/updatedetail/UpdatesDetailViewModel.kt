package com.simi.shelfpal.ui.updatedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.ui.updates.repository.UpdatesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdatesDetailViewModel(
    val items: List<Long>,
    val title: String,
    val subtitle: String
) : ViewModel(), KoinComponent {

    private val repository: UpdatesRepository by inject()

    private val _state = MutableStateFlow(UpdatesDetailState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUpdates(items).collect {
                _state.value = UpdatesDetailState(
                    items = it.toUpdateDetailItem(),
                    title = title,
                    subtitle = subtitle,
                )
            }
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
}

