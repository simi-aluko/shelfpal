package com.simi.shelfpal.ui.updates.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.onTertiaryContainerLight
import com.simi.shelfpal.ui.updates.ShelfPalUpdate
import com.simi.shelfpal.ui.updates.ShelfPalUpdateItem
import com.simi.shelfpal.ui.updates.UpdateHeaderIcon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.info
import shelfpal.composeapp.generated.resources.related_items

@Composable
fun UpdatesCard(
    data: ShelfPalUpdate,
    modifier: Modifier = Modifier
) {
    DefaultCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(Dimens.spacingSmallMedium)
        ) {
            UpdatesCardHeader(
                icon = data.headerIcon.icon,
                iconBackgroundColor = data.headerIcon.backgroundColor,
                iconTint = data.headerIcon.tint,
                title = data.title,
                body = data.subtitle,
                isNew = data.isNew
            )
            data.items?.let { items ->
                Column(modifier = Modifier.padding(top = Dimens.spacingSmallMedium)) {
                    Text(
                        text = stringResource(Res.string.related_items),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
                    ) {
                        repeat(items.size) {
                            UpdatesPill(data = data.items[it])
                        }
                    }
                }
            }
            UpdatesCardTimestamp(
                timestamp = data.timestamp,
                modifier = Modifier.padding(top = Dimens.spacingSmallMedium)
            )
        }
    }
}

@Composable
private fun UpdatesCardHeader(
    icon: DrawableResource,
    iconBackgroundColor: Color,
    iconTint: Color,
    title: String,
    body: String,
    isNew: Boolean?,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
    ) {
        UpdatesCardIcon(
            icon = icon,
            backgroundColor = iconBackgroundColor,
            tint = iconTint
        )

        Column(modifier = Modifier.weight(1f)) {
            UpdatesCardTitle(
                title = title,
                isNew = isNew,
            )

            Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))

            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun UpdatesCardIcon(
    icon: DrawableResource,
    backgroundColor: Color,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(Dimens.iconSizeXLarge)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        )
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(Dimens.iconSizeMediumLarge),
            tint = tint
        )
    }
}

@Composable
private fun UpdatesCardTitle(
    title: String,
    isNew: Boolean?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (isNew == true) {
            UpdatesBadge(text = "New")
        }
    }
}

@Composable
private fun UpdatesBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = onTertiaryContainerLight,
    contentColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusCircle))
            .background(backgroundColor)
            .padding(horizontal = Dimens.spacingSmall, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun UpdatesCardTimestamp(
    timestamp: String?,
    modifier: Modifier = Modifier
) {
    timestamp?.let {
        Text(
            text = timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun UpdatesCardPreview() {
    UpdatesCard(
        data = ShelfPalUpdate(
            title = "Update Title",
            subtitle = "Update Subtitle",
            status = ExpiryStatus.EXPIRED,
            timestamp = "12:00 PM",
            isNew = true,
            items = listOf(
                ShelfPalUpdateItem(
                    id = 1,
                    title = "Item 1",
                    category = "Beverages"
                )
            ),
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.info,
                tint = Color.White,
                backgroundColor = Color.Red.copy(alpha = 0.5f)
            )
        )
    )
}