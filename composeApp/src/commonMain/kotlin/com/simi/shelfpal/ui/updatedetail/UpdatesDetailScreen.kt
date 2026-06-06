package com.simi.shelfpal.ui.updatedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.home.composables.getProductCategoryImage
import com.simi.shelfpal.ui.shared.ItemColorStatus
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.shared.getStatusBorderColor
import com.simi.shelfpal.ui.shared.getStatusContainerColor
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.outlineVariantLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.mark_all_as_used
import shelfpal.composeapp.generated.resources.mark_as_used
import shelfpal.composeapp.generated.resources.no_expired_items
import shelfpal.composeapp.generated.resources.no_recent_activity
import shelfpal.composeapp.generated.resources.no_used_items

@Composable
fun UpdatesDetailScreen(
    params: UpdatesDetailParams,
    onNavigateBack: () -> Unit,
    onItemClick: (Long) -> Unit = {},
    viewModel: UpdatesDetailViewModel = viewModel { UpdatesDetailViewModel(params) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = state.title,
                subtitle = state.subtitle,
                onNavigateBack = onNavigateBack,
            )
        },
        bottomBar = {
            if (state.items.isNotEmpty() && params.showActions) {
                Surface(shadowElevation = Dimens.elevationMedium) {
                    Button(
                        onClick = { viewModel.markAllAsUsed() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = Dimens.spacingMedium,
                                start = Dimens.spacingMedium,
                                end = Dimens.spacingMedium,
                                bottom = Dimens.spacingXLarge
                            )
                            .height(Dimens.buttonHeight),
                    ) {
                        Text(text = stringResource(Res.string.mark_all_as_used))
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (state.items.isEmpty()) {
                EmptyScreen(params.entrySource)
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = Dimens.spacingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
                    contentPadding = PaddingValues(
                        top = Dimens.spacingMedium,
                        bottom = Dimens.spacingMedium
                    )
                ) {
                    items(state.items.size) { index ->
                        UpdateDetailItemRow(
                            showActions = params.showActions,
                            item = state.items[index],
                            status = getItemStatus(entrySource = params.entrySource),
                            onMarkAsUsed = { viewModel.markAsUsed(state.items[index].id) },
                            onItemClick = { onItemClick(state.items[index].id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UpdateDetailItemRow(
    showActions: Boolean = true,
    item: UpdatesDetailItem,
    status: ItemColorStatus?,
    onMarkAsUsed: () -> Unit,
    onItemClick: () -> Unit = {},
) {
    val categoryIcon = getProductCategoryImage(item.category)
    val backgroundColor = status?.let {
        getStatusContainerColor(it)
    } ?: MaterialTheme.colorScheme.surfaceVariant
    val iconBorderColor = status?.let { getStatusBorderColor(it) } ?: outlineVariantLight


    Card(
        modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
        colors = CardDefaults.cardColors(containerColor = onPrimaryLight),
        border = BorderStroke(1.dp, backgroundColor),
        shape = RoundedCornerShape(Dimens.radiusMedium),
    ) {
        Row(
            modifier = Modifier.padding(Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.iconSizeXLarge)
                    .clip(RoundedCornerShape(Dimens.spacingSmall))
                    .border(1.dp, iconBorderColor)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(categoryIcon),
                    contentDescription = null,
                    modifier = Modifier.height(Dimens.iconSizeMedium),
                )
            }
            Spacer(modifier = Modifier.width(Dimens.spacingSmall))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = Dimens.textSizeMediumLarge,
                    lineHeight = Dimens.textSizeMediumLarge,
                )
                Spacer(modifier = Modifier.size(Dimens.spacingExtraSmall))
                Text(
                    text = item.category,
                    fontSize = Dimens.textSizeXSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = Dimens.textSizeXSmall,
                )
            }
            Spacer(modifier = Modifier.width(Dimens.spacingSmall))
            if (showActions) {
                OutlinedButton(
                    onClick = onMarkAsUsed,
                    shape = RoundedCornerShape(Dimens.radiusCircle),
                ) {
                    Text(
                        text = stringResource(Res.string.mark_as_used),
                        fontSize = Dimens.textSizeXSmall,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyScreen(entrySource: UpdateDetailsEntrySource) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(getEmptyTitle(entrySource)),
            color = Color.Gray,
        )
    }
}

private fun getEmptyTitle(entrySource: UpdateDetailsEntrySource) = when(entrySource) {
    UpdateDetailsEntrySource.RECENT_ACTIVITY -> Res.string.no_recent_activity
    UpdateDetailsEntrySource.EXPIRED_MENU -> Res.string.no_expired_items
    UpdateDetailsEntrySource.USED_MENU -> Res.string.no_used_items
}

private fun getItemStatus(entrySource: UpdateDetailsEntrySource): ItemColorStatus? = when (entrySource) {
    UpdateDetailsEntrySource.EXPIRED_MENU -> ItemColorStatus.EXPIRED
    UpdateDetailsEntrySource.USED_MENU -> ItemColorStatus.USED
    UpdateDetailsEntrySource.RECENT_ACTIVITY -> null
}

@Preview
@Composable
private fun UpdatesDetailScreenPreview() {
    UpdateDetailItemRow(
        item = UpdatesDetailItem(
            id = 1,
            name = "Milk",
            category = "Dairy"
        ),
        status = ItemColorStatus.EXPIRED,
        onMarkAsUsed = {}
    )
}