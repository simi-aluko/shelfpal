package com.simi.shelfpal.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.home.composables.AddItemFAB
import com.simi.shelfpal.ui.home.composables.FilterBottomSheet
import com.simi.shelfpal.ui.home.composables.ShelfItems
import com.simi.shelfpal.ui.home.composables.TopRowFilter
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.categories
import shelfpal.composeapp.generated.resources.settings
import shelfpal.composeapp.generated.resources.shelfpal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
    navigateToAddItem: () -> Unit,
    navigateToItemDetail: (id: Long) -> Unit,
    navigateToSettings: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val categories = stringArrayResource(Res.array.categories)

    val shelfItems by viewModel.items.collectAsStateWithLifecycle()
    val bottomSheet by viewModel.bottomSheet.collectAsStateWithLifecycle()
    val filters by viewModel.filters.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeScreen(categories)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.shelfpal),
                actionIcon = Res.drawable.settings,
                actionIconClick = { navigateToSettings() }
            )
         },
        floatingActionButton = {
            AddItemFAB {
                navigateToAddItem()
            }
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            Column(
                modifier = Modifier.padding(
                    top = Dimens.spacingMedium,
                    start = Dimens.spacingMedium,
                    end = Dimens.spacingMedium,
                )
            ) {
                TopRowFilter(
                    filters = filters,
                    onFilterClick = {
                        coroutineScope.launch {
                            viewModel.onFilterClick(it)
                        }
                    }
                )
                Spacer(modifier = Modifier.size(Dimens.spacingMediumLarge))
                ShelfItems(
                    items = shelfItems,
                    onItemClick = {
                        navigateToItemDetail(it)
                    }
                )
                bottomSheet?.let {
                    FilterBottomSheet(
                        sheetState = sheetState,
                        bottomSheet = it,
                    ) {
                        viewModel.onBottomSheetDismiss()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(
        navigateToAddItem = {},
        navigateToItemDetail = {},
        navigateToSettings = {}
    )
}