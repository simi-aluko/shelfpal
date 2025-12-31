package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.simi.shelfpal.ui.details.models.ItemDetailExpiryInfo
import com.simi.shelfpal.ui.details.models.ItemDetailImages
import com.simi.shelfpal.ui.shared.composables.shimmerBrush
import com.simi.shelfpal.ui.theme.AppTheme
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.onErrorContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.error_loading_image
import shelfpal.composeapp.generated.resources.item_detail_expiry_date_
import shelfpal.composeapp.generated.resources.item_detail_time_remaining_
import shelfpal.composeapp.generated.resources.photoloadingerror

private const val IMAGE_RATIO = 4

@Composable
fun ItemDetailBody(
    expiryInfo: ItemDetailExpiryInfo,
    images: ItemDetailImages
) {
    ItemDetailCard {
        Column (
            modifier = Modifier.padding(Dimens.spacingMedium)
        ){
            ExpiryInfo(
                data = expiryInfo
            )
            Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
            ImageCarousel(
                data = images
            )
        }
    }
}



@Composable
private fun ExpiryInfo(
    data: ItemDetailExpiryInfo
) {
    Column {
        ItemDetailExpiryInfoRow(
            title = stringResource(Res.string.item_detail_expiry_date_),
            value = data.expiryDate,
            hasExpired = data.hasExpired
        )
        Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        data.timeRemaining?.let {
            ItemDetailExpiryInfoRow(
                title = stringResource(Res.string.item_detail_time_remaining_),
                value = data.timeRemaining,
                hasExpired = data.hasExpired
            )
        }
    }
}

@Composable
private fun ItemDetailExpiryInfoRow(
    title: String,
    value: String,
    hasExpired: Boolean
) {
    Row {
        ItemDetailTitle(
            text = title,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        ItemDetailTitleBody(
            text = value,
            hasExpired = hasExpired,
            modifier = Modifier.weight(1.5f)
        )
    }
}

@Composable
private fun ItemDetailTitle(
    text: String,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = Dimens.textSizeMedium,
        fontWeight = FontWeight.SemiBold,
        color = Color.DarkGray,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun ItemDetailTitleBody(
    text: String,
    hasExpired: Boolean,
    modifier: Modifier
) {
    val color = if (hasExpired) {
        errorLight
    } else {
        tertiaryLight
    }

    Text(
        modifier = modifier,
        text = text,
        fontSize = Dimens.textSizeMedium,
        fontWeight = FontWeight.SemiBold,
        color = color,
        textAlign = TextAlign.End
    )
}

@Composable
private fun ImageCarousel(
    data: ItemDetailImages
) {
    LazyRow(
        modifier = Modifier.clip(RoundedCornerShape(Dimens.spacingSmallMedium))
    ) {
        item {
            data.productImageUrl.takeIf { it.isNotEmpty() }?.let {
                ItemDetailImage(it)
            }
        }
        item {
            Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        }
        item {
            data.expiryImageUrl.takeIf { it.isNotEmpty() }?.let {
                ItemDetailImage(it)
            }
        }
    }
}

@Composable
private fun ItemDetailImage(url: String) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeightPx = windowInfo.containerSize.height
    val imageSizeDp = with(density) { (screenHeightPx / IMAGE_RATIO).toDp() }

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
            .size(imageSizeDp)
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
                    imageSize = imageSizeDp
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
            modifier = Modifier.height(imageSize/IMAGE_RATIO),
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

@Preview
@Composable
private fun ItemDetailBodyPreview() {
    AppTheme {
        ItemDetailBody(
            expiryInfo = ItemDetailExpiryInfo(
                expiryDate = "Expires 26th December, 2024",
                timeRemaining = "2 years, 3 months and 2 days"
            ),
            images = ItemDetailImages(
                productImageUrl = "https://picsum.photos/id/237/200/300",
                expiryImageUrl = "https://picsum.photos/id/230/200/300"
            )
        )
    }
}