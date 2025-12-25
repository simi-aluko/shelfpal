package com.simi.shelfpal.ui.home.models

import com.simi.shelfpal.ui.home.composables.BottomSheetHeaderViewModel

sealed class ViewStates {
    data class BottomSheet(
        val showBottomSheet: Boolean,
        val bottomSheetHeaderViewModel: BottomSheetHeaderViewModel,
        val filters: List<Filter>
    ) : ViewStates()
}

data class ViewMetaData(
    var selectedMonth: String? = null,
    var selectedYear: String? = null,
    var selectedCategory: String? = null,
    var categories: List<String> = emptyList()
)