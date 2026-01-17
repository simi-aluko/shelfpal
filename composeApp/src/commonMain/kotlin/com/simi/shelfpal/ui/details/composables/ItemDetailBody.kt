package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.simi.shelfpal.ui.details.models.ItemDetailExpiryInfo
import com.simi.shelfpal.ui.details.models.ItemDetailImages
import com.simi.shelfpal.ui.shared.SCREEN_IMAGE_RATIO
import com.simi.shelfpal.ui.shared.composables.UrlImage
import com.simi.shelfpal.ui.theme.AppTheme
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.item_detail_expiry_date_
import shelfpal.composeapp.generated.resources.item_detail_time_remaining_

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
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeightPx = windowInfo.containerSize.height
    val imageSizeDp = with(density) { (screenHeightPx / SCREEN_IMAGE_RATIO).toDp() }

    LazyRow(
        modifier = Modifier.clip(RoundedCornerShape(Dimens.spacingSmallMedium))
    ) {
        item {
            data.productImageUrl.takeIf { it.isNotEmpty() }?.let {
                UrlImage(it, imageSizeDp)
            }
        }
        item {
            Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        }
        item {
            data.expiryImageUrl.takeIf { it.isNotEmpty() }?.let {
                UrlImage(it, imageSizeDp)
            }
        }
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