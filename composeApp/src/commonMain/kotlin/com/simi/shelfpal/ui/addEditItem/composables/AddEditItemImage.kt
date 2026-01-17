package com.simi.shelfpal.ui.addEditItem.composables

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemImage
import com.simi.shelfpal.ui.shared.composables.UrlImage
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.secondaryContainerLight

@Composable
fun AddEditItemImageComposable(
    image: AddEditItemImage
) {
    val imageSize = Dimens.imageSizeAvatarMediumLarge

    when {
        image.bitmap != null && !image.isEdit -> {
            ImageContainer {
                Image(
                    bitmap = image.bitmap,
                    contentDescription = null,
                    modifier = Modifier.size(imageSize),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            }
        }

        image.url != null && image.isEdit -> {
            ImageContainer {
                UrlImage(
                    url = image.url,
                    imageSize = imageSize
                )
            }
        }
    }
}

@Composable
private fun ImageContainer(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.alpha(alpha = 0.8f)
            .padding(bottom = Dimens.spacingSmallMedium)
            .clip(RoundedCornerShape(Dimens.spacingSmall))
            .border(
                border = BorderStroke(2.dp, secondaryContainerLight),
                shape = RoundedCornerShape(Dimens.spacingSmall)
            )
    ) {
        content()
    }
}