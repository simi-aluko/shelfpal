package com.simi.shelfpal.ui.updates

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.updatedetail.UpdateDetailsEntrySource
import com.simi.shelfpal.ui.updatedetail.UpdatesDetailParams
import com.simi.shelfpal.ui.updates.composables.RecentActivity
import com.simi.shelfpal.ui.updates.composables.UpdatesSummary
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.expired_items
import shelfpal.composeapp.generated.resources.expired_items_subtitle
import shelfpal.composeapp.generated.resources.hourglasscheck
import shelfpal.composeapp.generated.resources.hourglassdisabled
import shelfpal.composeapp.generated.resources.shelf_updates
import shelfpal.composeapp.generated.resources.shelf_updates_subtitle
import shelfpal.composeapp.generated.resources.used_items
import shelfpal.composeapp.generated.resources.used_items_subtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesScreen(
    onNavigateToDetailClick: (UpdatesDetailParams) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    viewModel: UpdatesViewModel = viewModel { UpdatesViewModel() }
) {
    val summary: ShelfPalUpdate? by viewModel.aiSummary.collectAsStateWithLifecycle()
    val recentActivity: List<ShelfPalUpdate> by viewModel.recentActivity.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    val expiredTitle = stringResource(Res.string.expired_items)
    val expiredSubtitle = stringResource(Res.string.expired_items_subtitle)

    val usedTitle = stringResource(Res.string.used_items)
    val usedSubtitle = stringResource(Res.string.used_items_subtitle)

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.shelf_updates),
                subtitle = stringResource(Res.string.shelf_updates_subtitle),
                actionIcon = Res.drawable.hourglassdisabled,
                actionIcon2 = Res.drawable.hourglasscheck,
                actionIconClick = {
                    onNavigateToDetailClick(
                        UpdatesDetailParams(
                            items = emptyList(),
                            title = expiredTitle,
                            subtitle = expiredSubtitle,
                            showActions = false,
                            entrySource = UpdateDetailsEntrySource.EXPIRED_MENU
                        )
                    )
                },
                actionIcon2Click = {
                    onNavigateToDetailClick(
                        UpdatesDetailParams(
                            items = emptyList(),
                            title = usedTitle,
                            subtitle = usedSubtitle,
                            showActions = false,
                            entrySource = UpdateDetailsEntrySource.USED_MENU
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(top =innerPadding.calculateTopPadding())
        ) {
            LazyColumn (
                modifier = Modifier.padding(horizontal = Dimens.spacingMedium)
            ) {
                item {
                    Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                    UpdatesSummary(data = summary, loading = loadingState.isLoading)
                }
                item {
                    Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                    RecentActivity(updates = recentActivity, onCardClick = onNavigateToDetailClick, onItemClick = onItemClick)
                    Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                }
            }
        }
    }
}

@Composable
@Preview
private fun UpdatesScreenPreview() {
    UpdatesScreen()
}