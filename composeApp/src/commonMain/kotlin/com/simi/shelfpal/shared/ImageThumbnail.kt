package com.simi.shelfpal.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.theme.Dimens

@Composable
fun ImageThumbnail(
    imageBitmap: ImageBitmap
) {
    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = Modifier.size(Dimens.imageSizeAvatarLarge).alpha(alpha = 0.8f)
    )
}