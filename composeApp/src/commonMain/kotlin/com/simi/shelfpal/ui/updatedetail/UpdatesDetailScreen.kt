package com.simi.shelfpal.ui.updatedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.home.composables.getProductCategoryImage
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.outlineVariantLight
import org.jetbrains.compose.resources.painterResource

@Composable
fun UpdatesDetailScreen(
    params: UpdatesDetailParams,
    onNavigateBack: () -> Unit,
    onItemClick: (Long) -> Unit = {},
    viewModel: UpdatesDetailViewModel = viewModel {
        UpdatesDetailViewModel(
            items = params.items,
            title = params.title,
            subtitle = params.subtitle
        )
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                title = state.title,
                subtitle = state.subtitle,
                onNavigateBack = onNavigateBack,
            )
        },
        bottomBar = {
            if (state.items.isNotEmpty()) {
                Surface(shadowElevation = Dimens.elevationMedium) {
                    Button(
                        onClick = { viewModel.markAllAsUsed() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.spacingMedium)
                            .height(Dimens.buttonHeight),
                        shape = RoundedCornerShape(Dimens.radiusLarge),
                    ) {
                        Text(text = "Mark all as Used")
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
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
                        item = state.items[index],
                        onMarkAsUsed = { viewModel.markAsUsed(state.items[index].id) },
                        onItemClick = { onItemClick(state.items[index].id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun UpdateDetailItemRow(
    item: UpdatesDetailItem,
    onMarkAsUsed: () -> Unit,
    onItemClick: () -> Unit = {},
) {
    val categoryIcon = getProductCategoryImage(item.category)

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onItemClick() },
        colors = CardDefaults.cardColors(containerColor = onPrimaryLight),
        border = BorderStroke(1.dp, outlineVariantLight),
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
                    .background(MaterialTheme.colorScheme.surfaceVariant),
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
            OutlinedButton(
                onClick = onMarkAsUsed,
                shape = RoundedCornerShape(Dimens.radiusCircle),
            ) {
                Text(
                    text = "Mark as Used",
                    fontSize = Dimens.textSizeXSmall,
                )
            }
        }
    }
}
