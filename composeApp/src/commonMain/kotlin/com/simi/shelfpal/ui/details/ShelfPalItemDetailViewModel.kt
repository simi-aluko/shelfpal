package com.simi.shelfpal.ui.details

import androidx.lifecycle.ViewModel
import com.simi.shelfpal.ui.details.models.ViewStates
import com.simi.shelfpal.ui.details.models.toViewModel
import com.simi.shelfpal.ui.details.repository.ShelfPalItemDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShelfPalItemDetailViewModel(
    private val id: Long
) : ViewModel(), KoinComponent {

    private val repository by inject<ShelfPalItemDetailRepository>()

    private val _itemDetail = MutableStateFlow(ViewStates.ItemDetail())
    val detail = _itemDetail.asStateFlow()

    suspend fun init() {
        repository.getItem(id).collect {
            _itemDetail.value = it.toViewModel()
        }
    }
}