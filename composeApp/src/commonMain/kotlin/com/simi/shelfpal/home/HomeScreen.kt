package com.simi.shelfpal.home

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.simi.shelfpal.home.composables.BottomSheetHeaderViewModel
import com.simi.shelfpal.home.composables.FilterBottomSheet
import com.simi.shelfpal.home.composables.ShelfItem
import com.simi.shelfpal.home.composables.ShelfItems
import com.simi.shelfpal.home.composables.TopRowFilter
import com.simi.shelfpal.home.models.Filter
import com.simi.shelfpal.home.models.Filter.Category
import com.simi.shelfpal.shared.TopBar
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.onPrimaryContainerLight
import com.simi.shelfpal.theme.onPrimaryLight
import com.simi.shelfpal.theme.primaryContainerLight
import com.simi.shelfpal.theme.primaryLight
import com.simi.shelfpal.theme.secondaryContainerLight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_24px
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.all_items
import shelfpal.composeapp.generated.resources.apps
import shelfpal.composeapp.generated.resources.check_24px
import shelfpal.composeapp.generated.resources.chevrondown
import shelfpal.composeapp.generated.resources.food
import shelfpal.composeapp.generated.resources.packed_closed_check
import shelfpal.composeapp.generated.resources.settings
import shelfpal.composeapp.generated.resources.settings_24px
import shelfpal.composeapp.generated.resources.shelfpal
import shelfpal.composeapp.generated.resources.vegetable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToAddItem: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
                        Filter.Month(
                            title = "January",
                            onClick = { showBottomSheet = true },
                            rightIcon = Res.drawable.chevrondown
                        ),
                        Filter.Year(
                            title = "2025",
                            onClick = { showBottomSheet = true },
                            rightIcon = Res.drawable.chevrondown
                        )
                    )
                )
                Spacer(modifier = Modifier.size(Dimens.spacingMediumLarge))
                ShelfItems(
                    items = listOf(
                        ShelfItem(
                            id = 2,
                            productName = "Protein",
                            productImage = Res.drawable.food,
                            expiryText = "Expires Nov 4",
                            expired = true,
                            durationRemaining = "Expired"
                        ),
                        ShelfItem(
                            id = 1,
                            productName = "Milk",
                            productImage = Res.drawable.vegetable,
                            expiryText = "Expires Nov 4",
                            expired = false,
                            durationRemaining = "2 days"
                        ),
                        ShelfItem(
                            id = 1,
                            productName = "Milk",
                            productImage = Res.drawable.vegetable,
                            expiryText = "Expires Nov 4",
                            expired = false,
                            durationRemaining = "14 days"
                        ),
                    )
                )
                FilterBottomSheet(
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