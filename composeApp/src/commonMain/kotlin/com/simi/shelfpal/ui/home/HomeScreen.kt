package com.simi.shelfpal.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.home.composables.BottomSheetHeaderViewModel
import com.simi.shelfpal.ui.home.composables.ShelfItems
import com.simi.shelfpal.ui.home.composables.TopRowFilter
import com.simi.shelfpal.ui.home.models.Filter.Category
import com.simi.shelfpal.ui.home.models.Filter.Month
import com.simi.shelfpal.ui.home.models.Filter.Year
import com.simi.shelfpal.ui.shared.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_24px
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.all_items
import shelfpal.composeapp.generated.resources.apps
import shelfpal.composeapp.generated.resources.check_24px
import shelfpal.composeapp.generated.resources.chevrondown
import shelfpal.composeapp.generated.resources.packed_closed_check
import shelfpal.composeapp.generated.resources.shelfpal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel { HomeViewModel() },
    navigateToAddItem: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val shelfItems by viewModel.items.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.shelfpal),
                actionIcon = Res.drawable.packed_closed_check,
                actionIconClick = {}
            )
         },
        floatingActionButton = {
            AddItemFAB {
                navigateToAddItem()
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.spacingMedium)
            ) {
                TopRowFilter(
                    filters = setOf(
                        Category(
                            title = stringResource(Res.string.all_items),
                            leftIcon = Res.drawable.apps,
                            onClick = { showBottomSheet = true },
                            rightIcon = Res.drawable.chevrondown
                        ),
                        Month(
                            title = "January",
                            onClick = { showBottomSheet = true },
                            rightIcon = Res.drawable.chevrondown
                        ),
                        Year(
                            title = "2025",
                            onClick = { showBottomSheet = true },
                            rightIcon = Res.drawable.chevrondown
                        )
                    )
                )
                Spacer(modifier = Modifier.size(Dimens.spacingMediumLarge))
                ShelfItems(items = shelfItems)
                _root_ide_package_.com.simi.shelfpal.ui.home.composables.FilterBottomSheet(
                    sheetState = sheetState,
                    showBottomSheet = showBottomSheet,
                    bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
                        title = "Filter by Category",
                        subtitle = "Choose a category to filter your items"
                    ),
                    filters = listOf(
                        Category(
                            title = stringResource(Res.string.all_items),
                            leftIcon = Res.drawable.apps,
                            selected = true,
                            rightIcon = Res.drawable.check_24px,
                            onClick = {
                                scope.launch { sheetState.hide() }
                                showBottomSheet = false
                            }
                        ),
                        Category(
                            title = stringResource(Res.string.all_items),
                            leftIcon = Res.drawable.apps,
                            selected = false,
                            rightIcon = null,
                            onClick = {
                                scope.launch { sheetState.hide() }
                                showBottomSheet = false
                            }
                        ),
                        Category(
                            title = stringResource(Res.string.all_items),
                            leftIcon = Res.drawable.apps,
                            selected = false,
                            rightIcon = null,
                            onClick = {
                                scope.launch { sheetState.hide() }
                                showBottomSheet = false
                            }
                        )
                    ),
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}

@Composable
private fun AddItemFAB(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.size(Dimens.imageSizeAvatarLarge),
        onClick = onClick,
        shape = CircleShape,
        colors = IconButtonColors(
            containerColor = primaryLight,
            contentColor = onPrimaryLight,
            disabledContainerColor = secondaryContainerLight,
            disabledContentColor = onPrimaryContainerLight
        )
    ){
        Icon(
            imageVector = vectorResource(Res.drawable.add_24px),
            contentDescription = stringResource(Res.string.add_item)
        )
    }
}