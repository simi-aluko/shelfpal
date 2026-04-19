package com.simi.shelfpal.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.details.composables.ItemDetailActions
import com.simi.shelfpal.ui.details.composables.ItemDetailBody
import com.simi.shelfpal.ui.details.composables.ItemDetailSettings
import com.simi.shelfpal.ui.details.composables.ItemDetailTopBar
import com.simi.shelfpal.ui.details.models.ViewStates
import com.simi.shelfpal.ui.theme.Dimens
import kotlinx.coroutines.launch

@Composable
fun ShelfPalItemDetailScreen(
    id: Long,
    onNavigationIconClick: () -> Unit,
    onActionIconClick: (ViewStates.ItemDetail) -> Unit,
    showActions: Boolean = true,
    viewModel: ShelfPalItemDetailViewModel = viewModel { ShelfPalItemDetailViewModel(id) },
) {

    val coroutineScope = rememberCoroutineScope()
    val itemDetail by viewModel.detail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.getItemDetails {
            onNavigationIconClick()
        }
    }

    Scaffold(
        topBar = {
            val actionIconClick: (() -> Unit)? = if (showActions) {
                { onActionIconClick(itemDetail) }
            } else null
            ItemDetailTopBar(
                viewData = itemDetail.itemDetailHeader,
                onNavigationIconClick = onNavigationIconClick,
                onActionIconClick = actionIconClick
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimens.spacingMedium)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
            ) {
                ItemDetailBody(
                    expiryInfo = itemDetail.itemDetailExpiryInfo,
                    images = itemDetail.itemDetailImages
                )
                ItemDetailSettings(
                    data = itemDetail.reminderSettings,
                    onCheckClick = {
                        coroutineScope.launch {
                            viewModel.updateReminderSettings(it)
                        }
                    },
                    enabled = showActions,
                )
                if (showActions) {
                    ItemDetailActions(
                        hasExpired = itemDetail.reminderSettings.hasExpired,
                        onTestClick = {
                            // TODO: Implement the test notification
                        },
                        onDeleteClick = {
                            coroutineScope.launch {
                                viewModel.deleteItem()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.size(Dimens.spacingLarge))
            }
        }
    }
}
