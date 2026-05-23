package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.errorLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.delete
import shelfpal.composeapp.generated.resources.delete_item

@Composable
fun ItemDetailActions(
    onDeleteClick: () -> Unit
) {
    ItemDetailCard {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingExtraSmall)
        ) {
            ItemDetailAction(
                icon = Res.drawable.delete,
                text = stringResource(Res.string.delete_item),
                contentTint = errorLight,
                borderColor = errorLight,
                onClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun ItemDetailAction(
    icon: DrawableResource,
    text: String,
    borderColor: Color,
    contentTint: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        border = BorderStroke(1.dp, color = borderColor)
    ) {
        Icon(
            painter = painterResource(icon),
            tint = contentTint,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = contentTint
        )
    }
}