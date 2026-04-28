package com.simi.shelfpal.ui.updates.composables

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
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import com.simi.shelfpal.ui.updates.ShelfPalUpdate
import com.simi.shelfpal.ui.updates.UpdateHeaderIcon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.empty_summary
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.shelf_summary

@Composable
fun UpdatesSummary(
    data: ShelfPalUpdate?,
    loading: Boolean
) {
    Column {
        Text(
            text = stringResource(Res.string.shelf_summary),
            fontSize = Dimens.textSizeLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSmallMedium))
        when {
            loading -> UpdatesCardShimmer(showRelatedItems = false)
            data != null -> UpdatesCard(data = data)
            else -> EmptySummary()
        }
    }
}

@Composable
private fun EmptySummary() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingLarge),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.empty_summary),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Preview
@Composable
private fun UpdatesSummaryPreview() {
    UpdatesSummary(
        data = ShelfPalUpdate(
            title = "Update Title",
            subtitle = "Update Subtitle",
            isNew = null,
            items = null,
            headerIcon = UpdateHeaderIcon(
                icon = Res.drawable.notifications_24px,
                tint = tertiaryLight,
                backgroundColor = primaryContainerLight,
            )
        ),
        loading = true
    )
}