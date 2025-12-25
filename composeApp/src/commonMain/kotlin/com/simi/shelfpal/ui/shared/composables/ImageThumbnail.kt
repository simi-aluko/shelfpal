package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.secondaryContainerLight

@Composable
fun ImageThumbnail(
    imageBitmap: ImageBitmap
) {
    Box(
        modifier = Modifier
            .padding(bottom = Dimens.spacingSmallMedium)
            .clip(RoundedCornerShape(Dimens.spacingSmall))
            .border(
                border = BorderStroke(2.dp, secondaryContainerLight),
                shape = RoundedCornerShape(Dimens.spacingSmall)
            )
    ){
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.imageSizeAvatarMediumLarge)
                .alpha(alpha = 0.8f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
    }
}
