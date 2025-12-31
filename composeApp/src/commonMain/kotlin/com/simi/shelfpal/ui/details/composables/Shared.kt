package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.theme.Dimens

@Composable
fun ItemDetailCard(
    content: @Composable () -> Unit
) {
    ElevatedCard(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        shape = RoundedCornerShape(Dimens.spacingMedium)
    ) {
        content()
    }
}

@Composable
fun ItemDetailSectionHeader(
    title: String
) {
    Text(
        text = title,
        fontSize = Dimens.textSizeMediumLarge,
        fontWeight = FontWeight.SemiBold
    )
}