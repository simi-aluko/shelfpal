package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.simi.shelfpal.ui.shared.SCREEN_IMAGE_RATIO
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onErrorContainerLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.error_loading_image
import shelfpal.composeapp.generated.resources.photoloadingerror

@Composable
fun UrlImage(
    url: String,
    imageSize: Dp
) {

    var isLoading by remember { mutableStateOf(false) }
    var onError by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .crossfade(true)
            .listener(
                onError = { _, result ->
                    onError = true
                    isLoading = false
                },
                onStart = {
                    isLoading = true
                    onError = false
                },
                onSuccess = { _, result ->
                    isLoading = false
                    onError = false
                }
            ).build()
    )

    Box(
        modifier = Modifier
            .size(imageSize)
            .clip(RoundedCornerShape(Dimens.spacingSmall))
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(shimmerBrush())
                )
            }
            onError -> {
                ImageError(
                    modifier = Modifier.matchParentSize(),
                    onClick = { painter.restart() },
                    imageSize = imageSize
                )
            }
            else -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun ImageError(
    modifier: Modifier,
    onClick: () -> Unit,
    imageSize: Dp
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(Dimens.spacingSmall))
            .border(1.dp, Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.height(imageSize/SCREEN_IMAGE_RATIO),
            painter = painterResource(Res.drawable.photoloadingerror),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Text(
            modifier = Modifier.padding(Dimens.spacingSmall),
            text = stringResource(Res.string.error_loading_image),
            color = onErrorContainerLight,
            textAlign = TextAlign.Center,
            fontSize = Dimens.textSizeSmall
        )
    }
}