package com.simi.shelfpal.ui.updates

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UpdatesViewModel : ViewModel() {
    private val _updates = MutableStateFlow<List<ShelfPalUpdate>>(emptyList())
    val updates = _updates.asStateFlow()

    private val _aiSummary = MutableStateFlow<ShelfPalUpdate?>(null)
    val aiSummary = _aiSummary.asStateFlow()
}