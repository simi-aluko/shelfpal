package com.simi.shelfpal.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.ui.home.models.HomeShelfItem
import com.simi.shelfpal.ui.home.models.toHomeShelfItem
import com.simi.shelfpal.ui.home.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.mp.KoinPlatform.getKoin

class HomeViewModel() : ViewModel(), KoinComponent {
    private val homeRepository by inject<HomeRepository>()

    private val _items = MutableStateFlow<List<HomeShelfItem>>(emptyList())
    val items: StateFlow<List<HomeShelfItem>> = _items

    init {
        getAllItems()
    }

    fun getAllItems() {
        viewModelScope.launch {
            homeRepository.getItems().collect {
                _items.value = it.map { item ->
                    item.toHomeShelfItem()
                }
            }
        }
    }
}