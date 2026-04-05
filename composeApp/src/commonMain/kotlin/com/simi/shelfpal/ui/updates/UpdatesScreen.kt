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
import com.simi.shelfpal.ui.updates.composables.RecentActivity
import com.simi.shelfpal.ui.updates.composables.UpdatesShimmer
import com.simi.shelfpal.ui.updates.composables.UpdatesSummary
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.shelf_updates
import shelfpal.composeapp.generated.resources.shelf_updates_subtitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesScreen(
    viewModel: UpdatesViewModel = viewModel { UpdatesViewModel() }
) {
    val summary: ShelfPalUpdate? by viewModel.aiSummary.collectAsStateWithLifecycle()
    val recentActivity: List<ShelfPalUpdate> by viewModel.updates.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.shelf_updates),
                subtitle = stringResource(Res.string.shelf_updates_subtitle)
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(top =innerPadding.calculateTopPadding())
        ) {
            when {
                loadingState.isLoading -> UpdatesShimmer()
                else -> {
                    LazyColumn (
                        modifier = Modifier.padding(horizontal = Dimens.spacingMedium)
                    ) {
                        summary?.let {
                            item {
                                Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                                UpdatesSummary(data = it)
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                            RecentActivity(updates = recentActivity)
                            Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
                        }
                    }
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