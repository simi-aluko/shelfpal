package com.simi.shelfpal.ui.imagecapture.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.carousel_header_image

@Composable
fun PermissionDenied(
    permissionDeniedDrawable: DrawableResource
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        ElevatedCard (
            shape = CircleShape,
            modifier = Modifier.size(Dimens.imageSizeHero)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(secondaryContainerLight),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(permissionDeniedDrawable),
                    modifier = Modifier.size(Dimens.imageSizeLogo),
                    contentDescription = stringResource(Res.string.carousel_header_image),
                    colorFilter = ColorFilter.tint(primaryLight)
                )
            }
        }
    }
}