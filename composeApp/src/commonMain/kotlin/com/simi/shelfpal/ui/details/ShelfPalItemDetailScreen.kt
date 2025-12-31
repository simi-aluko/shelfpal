package com.simi.shelfpal.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.details.composables.ItemDetailBody
import com.simi.shelfpal.ui.details.composables.ItemDetailTopBar
import com.simi.shelfpal.ui.details.models.ViewStates

@Composable
fun ShelfPalItemDetailScreen(
    id: Long,
    onNavigationIconClick: () -> Unit,
    onActionIconClick: (ViewStates.ItemDetail) -> Unit,
    viewModel: ShelfPalItemDetailViewModel = viewModel { ShelfPalItemDetailViewModel(id) },
) {

    val itemDetail by viewModel.detail.collectAsStateWithLifecycle()

    LaunchedEffect(Unit){
        viewModel.init()
    }

    Scaffold(
        topBar = {
            ItemDetailTopBar(
                viewData = itemDetail.itemDetailHeader,
                onNavigationIconClick = onNavigationIconClick,
                onActionIconClick = {
                    onActionIconClick(itemDetail)
                }
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column {
                ItemDetailBody(
                    expiryInfo = itemDetail.itemDetailExpiryInfo,
                    images = itemDetail.itemDetailImages
                )
            }
        }
    }
}
