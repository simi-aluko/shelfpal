package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight

@Composable
fun DefaultCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.radiusLarge),
        colors = CardDefaults.cardColors(
            containerColor = onPrimaryLight
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.elevationSmall)
    ) {
        content()
    }
}