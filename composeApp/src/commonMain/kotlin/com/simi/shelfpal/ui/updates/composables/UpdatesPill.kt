package com.simi.shelfpal.ui.updates.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.shared.getExpiredBorderColor
import com.simi.shelfpal.ui.shared.getExpiredContainerColor
import com.simi.shelfpal.ui.shared.getExpiredLabelColor
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.updates.ShelfPalUpdateItem
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.chevron_right
import shelfpal.composeapp.generated.resources.food

@Composable
fun UpdatesPill(
    expired: Boolean = false,
    data: ShelfPalUpdateItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusCircle))
            .border(
                width = 1.dp,
                color = getExpiredBorderColor(expired),
                shape = RoundedCornerShape(Dimens.radiusCircle)
            )
            .background(getExpiredContainerColor(expired))
            .clickable { data.onClick(data.id) }
            .padding(
                start = Dimens.spacingSmallMedium,
                end = Dimens.spacingExtraSmall,
                top = Dimens.spacingExtraSmall,
                bottom = Dimens.spacingExtraSmall,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        Image(
            painter = painterResource(data.icon),
            contentDescription = null,
            modifier = Modifier.size(Dimens.iconSizeSmall),
        )
        Text(
            text = data.title,
            fontSize = Dimens.textSizeXSmall,
            color = getExpiredLabelColor(expired)
        )
        Icon(
            painter = painterResource(Res.drawable.chevron_right),
            contentDescription = null,
            modifier = Modifier.size(Dimens.iconSizeSmall),
            tint = getExpiredLabelColor(expired)
        )
    }
}

@Preview
@Composable
private fun UpdatesPill() {
    UpdatesPill(
        data = ShelfPalUpdateItem(
            id = 1,
            title = "Item 1",
            icon = Res.drawable.food
        )
    )
}
