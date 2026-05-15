package com.simi.shelfpal.ui.home.models

import com.simi.shelfpal.ui.home.composables.BottomSheetHeaderViewModel
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import kotlinx.datetime.Month
import kotlinx.datetime.number
import org.jetbrains.compose.resources.getString
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.category_all_categories

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
    var selectedItemStatus: String? = null,
    var categories: List<String> = emptyList()
) {
    fun getFilterMonth(): Int? {
        return Month.entries.find { it.name.capitalizeFirstChar() == selectedMonth }?.number
    }

    fun getFilterYear(): Int? {
        return selectedYear?.toIntOrNull()
    }

    suspend fun getFilterCategory() = if (selectedCategory == getString(Res.string.category_all_categories)) {
        null
    } else {
        selectedCategory
    }
}