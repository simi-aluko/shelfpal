package com.simi.shelfpal.ui.updates.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onSecondaryContainerLight
import com.simi.shelfpal.ui.theme.onTertiaryContainerLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import com.simi.shelfpal.ui.theme.tertiaryContainerLight
import com.simi.shelfpal.ui.updatedetail.UpdatesDetailParams
import com.simi.shelfpal.ui.updates.ShelfPalUpdate
import com.simi.shelfpal.ui.updates.ShelfPalUpdateItem
import com.simi.shelfpal.ui.updates.UpdateHeaderIcon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.no_recent_activity
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.recent_activity

@Composable
fun RecentActivity(
    updates: List<ShelfPalUpdate>,
    modifier: Modifier = Modifier,
    onCardClick: ((UpdatesDetailParams) -> Unit)? = null,
    onItemClick: (Long) -> Unit = {},
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
        Text(
            text = stringResource(Res.string.recent_activity),
            fontSize = Dimens.textSizeLarge,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSmallMedium))
        if (updates.isEmpty()) {
            EmptyRecentActivity()
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)) {
                updates.forEach { update ->
                    UpdatesCard(data = update, onCardClick = onCardClick, onItemClick = onItemClick)
                }
            }
        }
        Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
    }
}

@Composable
private fun EmptyRecentActivity() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingLarge),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.no_recent_activity),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
private fun RecentActivityPreview() {
    RecentActivity(
        updates = listOf(
            ShelfPalUpdate(
                title = "Items Expiring Soon",
                subtitle = "Organic Milk expires in 2 days",
                isNew = true,
                items = listOf(
                    ShelfPalUpdateItem(1, "Organic Milk", "Beverages")
                ),
                headerIcon = UpdateHeaderIcon(
                    icon = Res.drawable.notifications_24px,
                    tint = onSecondaryContainerLight,
                    backgroundColor = secondaryContainerLight
                )
            ),
            ShelfPalUpdate(
                title = "Weekly Summary",
                subtitle = "You have 4 items tracked this week",
                isNew = false,
                items = listOf(
                    ShelfPalUpdateItem(1, "Organic Milk", "Beverages"),
                    ShelfPalUpdateItem(2, "Greek Yogurt", "Beverages"),
                    ShelfPalUpdateItem(3, "Face Cream", "Beverages"),
                    ShelfPalUpdateItem(4, "Vitamin D3", "Beverages")
                ),
                headerIcon = UpdateHeaderIcon(
                    icon = Res.drawable.notifications_24px,
                    tint = onTertiaryContainerLight,
                    backgroundColor = tertiaryContainerLight
                )
            )
        )
    )
}