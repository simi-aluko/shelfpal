package com.simi.shelfpal.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.ui.additem.utils.getSelectableYearList
import com.simi.shelfpal.ui.home.composables.BottomSheetHeaderViewModel
import com.simi.shelfpal.ui.home.models.Filter
import com.simi.shelfpal.ui.home.models.HomeShelfItem
import com.simi.shelfpal.ui.home.models.ViewMetaData
import com.simi.shelfpal.ui.home.models.ViewStates.BottomSheet
import com.simi.shelfpal.ui.home.models.toHomeShelfItem
import com.simi.shelfpal.ui.home.repository.HomeRepository
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.getString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.all_months
import shelfpal.composeapp.generated.resources.all_years
import shelfpal.composeapp.generated.resources.category_all_categories
import shelfpal.composeapp.generated.resources.chevrondown
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

    private val _bottomSheet = MutableStateFlow<BottomSheet?>(null)
    val bottomSheet = _bottomSheet.asStateFlow()

    private val _filters = MutableStateFlow<Set<Filter>>(emptySet())
    val filters = _filters.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: StateFlow<List<HomeShelfItem>> = filters.flatMapLatest {
        homeRepository.getItems(
            category = viewMetaData.getFilterCategory(),
            month = viewMetaData.getFilterMonth(),
            year = viewMetaData.getFilterYear()
        )
    }.map { items ->
        items.map {
            it.toHomeShelfItem()
        }
    }.stateIn(viewModelScope, WhileSubscribed(), emptyList())

    suspend fun initializeScreen(categories: List<String>) {
        viewMetaData.categories = categories
        initializeFilters()
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun initializeFilters() {
        val now = Clock.System.now()
        val localTimeZone = TimeZone.currentSystemDefault()
        val currentTime = now.toLocalDateTime(localTimeZone)

        viewMetaData.selectedMonth = getString(Res.string.all_months)

        viewMetaData.selectedYear = getString(Res.string.all_years)

        viewMetaData.selectedCategory = getString(Res.string.category_all_categories)

        _filters.value = setOf(
            Filter.Category(
                title = viewMetaData.selectedCategory.orEmpty(),
                rightIcon = Res.drawable.chevrondown
            ),
            Filter.Month(
                title = viewMetaData.selectedMonth.orEmpty(),
                rightIcon = Res.drawable.chevrondown
            ),
            Filter.Year(
                title = viewMetaData.selectedYear.orEmpty(),
                rightIcon = Res.drawable.chevrondown
            )
        )
    }

    suspend fun onFilterClick(filter: Filter) {
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
                    filters = getYearList()
                )
            }
        }
    }

    fun onBottomSheetDismiss() {
        _bottomSheet.value = _bottomSheet.value?.copy(showBottomSheet = false)
    }

    private suspend fun getMonthList(): List<Filter.Month> {
        return listOf(getString(Res.string.all_months))
            .plus(getDisplayMonths())
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

    private suspend fun getYearList(): List<Filter.Year> {
        return listOf(getString(Res.string.all_years))
            .plus(getSelectableYearList())
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