package com.simi.shelfpal.ui.updates.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import com.simi.shelfpal.ui.updates.ShelfPalUpdate
import com.simi.shelfpal.ui.updates.UpdateHeaderIcon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.shelf_summary

@Composable
fun UpdatesSummary(
    data: ShelfPalUpdate,
    loading: Boolean
) {
    Column {
        Text(
            text = stringResource(Res.string.shelf_summary),
            fontSize = Dimens.textSizeLarge,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSmallMedium))
        if (loading) {
            UpdatesCardShimmer(showRelatedItems = false)
        } else {
            UpdatesCard(
                data = data
            )
        }
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