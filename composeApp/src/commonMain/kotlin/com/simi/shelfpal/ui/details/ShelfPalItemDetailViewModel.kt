package com.simi.shelfpal.ui.details

import androidx.lifecycle.ViewModel
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettings
import com.simi.shelfpal.ui.details.models.ViewMetaData
import com.simi.shelfpal.ui.details.models.ViewStates
import com.simi.shelfpal.ui.details.models.toViewModel
import com.simi.shelfpal.ui.details.repository.ShelfPalItemDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShelfPalItemDetailViewModel(
    private val id: Long,
    private val viewMetaData: ViewMetaData = ViewMetaData()
) : ViewModel(), KoinComponent {

    private val repository by inject<ShelfPalItemDetailRepository>()

    private val _itemDetail = MutableStateFlow(ViewStates.ItemDetail())
    val detail = _itemDetail.asStateFlow()

    suspend fun getItemDetails(
        onItemNotFound: () -> Unit
    ) {
        repository.getItem(id).collect {
            if (it != null) {
                viewMetaData.shelfPalItem = it
                _itemDetail.value = it.toViewModel()
            } else {
                onItemNotFound()
            }
        }
    }

    suspend fun updateReminderSettings(
        updatedSettings: ShelfPalItemReminderSettings
    ) {
        viewMetaData.shelfPalItem?.let {
            repository.updateItem(it.copy(reminderSettings = updatedSettings))
        }
    }

    suspend fun deleteItem() {
        viewMetaData.shelfPalItem?.let {
            repository.deleteItem(it)
        }
    }
}