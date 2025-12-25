package com.simi.shelfpal.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.ui.home.composables.BottomSheetHeaderViewModel
import com.simi.shelfpal.ui.home.models.Filter
import com.simi.shelfpal.ui.home.models.HomeShelfItem
import com.simi.shelfpal.ui.home.models.ViewMetaData
import com.simi.shelfpal.ui.home.models.ViewStates.BottomSheet
import com.simi.shelfpal.ui.home.models.toHomeShelfItem
import com.simi.shelfpal.ui.home.repository.HomeRepository
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.category_all_categories
import shelfpal.composeapp.generated.resources.expiry_month
import shelfpal.composeapp.generated.resources.expiry_year
import shelfpal.composeapp.generated.resources.product_category
import shelfpal.composeapp.generated.resources.select_category
import shelfpal.composeapp.generated.resources.select_month
import shelfpal.composeapp.generated.resources.select_year
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val viewMetaData: ViewMetaData = ViewMetaData()
) : ViewModel(), KoinComponent {
    private val homeRepository by inject<HomeRepository>()

    private val _items = MutableStateFlow<List<HomeShelfItem>>(emptyList())
    val items: StateFlow<List<HomeShelfItem>> = _items

    private val _bottomSheet = MutableStateFlow<BottomSheet?>(null)
    val bottomSheet = _bottomSheet.asStateFlow()

    private val _filters = MutableStateFlow<Set<Filter>>(emptySet())
    val filters = _filters.asStateFlow()

    fun initializeScreen(categories: List<String>) {
        viewMetaData.categories = categories
        getAllItems()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            homeRepository.getItems().collect {
                _items.value = it.map { item ->
                    item.toHomeShelfItem()
                }
                initializeFilters()
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun initializeFilters() {
        val now = Clock.System.now()
        val localTimeZone = TimeZone.currentSystemDefault()
        val currentTime = now.toLocalDateTime(localTimeZone)

        viewMetaData.selectedMonth = currentTime.month.name.capitalizeFirstChar()

        viewMetaData.selectedYear = currentTime.year.toString()

        viewModelScope.launch {
            viewMetaData.selectedCategory = getString(Res.string.category_all_categories)
        }

        _filters.value = setOf(
            Filter.Category(title = viewMetaData.selectedCategory.orEmpty()),
            Filter.Month(title = viewMetaData.selectedMonth.orEmpty()),
            Filter.Year(title = viewMetaData.selectedYear.orEmpty())
        )
    }

    fun onFilterClick(filter: Filter) {
        when (filter) {
            is Filter.Category -> {
                _bottomSheet.value = BottomSheet(
                    showBottomSheet = true,
                    bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
                        title = Res.string.product_category,
                        subtitle = Res.string.select_category
                    ),
                    filters = getCategoryList(viewMetaData.categories)
                )
            }
            is Filter.Month -> {
                _bottomSheet.value = BottomSheet(
                    showBottomSheet = true,
                    bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
                        title = Res.string.expiry_month,
                        subtitle = Res.string.select_month
                    ),
                    filters = getMonthList()
                )
            }
            is Filter.Year -> {
                _bottomSheet.value = BottomSheet(
                    showBottomSheet = true,
                    bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
                        title = Res.string.expiry_year,
                        subtitle = Res.string.select_year
                    ),
                    filters = getYearList(_items.value)
                )
            }
        }
    }

    fun onBottomSheetDismiss() {
        _bottomSheet.value = _bottomSheet.value?.copy(showBottomSheet = false)
    }

    private fun getMonthList(): List<Filter.Month> {
        return Month.entries
            .map { month ->
                month.name.capitalizeFirstChar()
            }
            .map { month ->
                Filter.Month(
                    title = month,
                    selected = month == viewMetaData.selectedMonth,
                    onClick = {
                        _bottomSheet.value = _bottomSheet.value?.copy(showBottomSheet = false)
                        viewMetaData.selectedMonth = month
                        _filters.value = _filters.value.map {
                            if (it is Filter.Month) {
                                it.copy(title = month)
                            } else {
                                it
                            }
                        }.toSet()
                    }
                )
            }
    }

    private fun getCategoryList(categories: List<String>): List<Filter.Category> {
        return categories.map { category ->
            Filter.Category(
                title = category,
                selected = category == viewMetaData.selectedCategory,
                onClick = {
                    _bottomSheet.value = _bottomSheet.value?.copy(showBottomSheet = false)
                    viewMetaData.selectedCategory = category
                    _filters.value = _filters.value.map {
                        if (it is Filter.Category) {
                            it.copy(title = category)
                        } else {
                            it
                        }
                    }.toSet()
                }
            )
        }
    }

    private fun getYearList(items: List<HomeShelfItem>): List<Filter.Year> {
        return items.mapNotNull { it.expiryYear }.sortedBy { it }.distinct().map { it.toString() }
            .map { year ->
                Filter.Year(
                    title = year,
                    selected = year == viewMetaData.selectedYear,
                    onClick = {
                        _bottomSheet.value = _bottomSheet.value?.copy(showBottomSheet = false)
                        viewMetaData.selectedYear = year
                        _filters.value = _filters.value.map {
                            if (it is Filter.Year) {
                                it.copy(title = year)
                            } else {
                                it
                            }
                        }.toSet()
                    }
                )
            }
    }
}