package com.simi.shelfpal.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.M
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.errorContainerLight
import com.simi.shelfpal.theme.errorDark
import com.simi.shelfpal.theme.errorLight
import com.simi.shelfpal.theme.inversePrimaryLight
import com.simi.shelfpal.theme.onPrimaryLight
import com.simi.shelfpal.theme.outlineVariantLight
import com.simi.shelfpal.theme.primaryContainerLight
import com.simi.shelfpal.theme.primaryLight
import com.simi.shelfpal.theme.scrimLight
import com.simi.shelfpal.theme.secondaryLight
import com.simi.shelfpal.theme.surfaceContainerLight
import com.simi.shelfpal.theme.tertiaryLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.vegetable
import kotlin.math.exp

@Composable
fun ShelfItems(
    modifier: Modifier = Modifier,
    items: List<ShelfItem>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        items(items.size) { index ->
            ShelfItem(shelfItem = items[index])
        }
    }
}

@Composable
private fun ShelfItem(
    shelfItem: ShelfItem
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = onPrimaryLight),
        border = BorderStroke(1.dp, outlineVariantLight),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Dimens.spacingSmall)
        ) {
            ProductImage(
                expired = shelfItem.expired,
                icon = shelfItem.productImage
            )
            Spacer(modifier = Modifier.width(Dimens.spacingSmall))
            ProductInfo(
                productName = shelfItem.productName,
                expiryInfo = shelfItem.expiryText
            )
            Spacer(modifier = Modifier.weight(1f))
            ExpiredLabel(
                expired = shelfItem.expired,
                label = shelfItem.durationRemaining
            )
        }
    }
}

@Composable
private fun ProductInfo(
    productName: String,
    expiryInfo: String
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = productName,
            fontWeight = FontWeight.Bold,
            fontSize = Dimens.textSizeMediumLarge
        )
        Text(
            text = expiryInfo,
            fontSize = Dimens.textSizeXSmall,
            color = secondaryLight
        )
    }
}

@Composable
private fun ProductImage(
    expired: Boolean,
    icon: DrawableResource
) {
    Card (
        shape = RoundedCornerShape(Dimens.spacingSmall),
        modifier = Modifier.size(Dimens.iconSizeXLarge),
        border = BorderStroke(1.dp, getExpiredBorderColor(expired))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(getExpiredContainerColor(expired)),
        ) {
            Image(
                modifier = Modifier.height(Dimens.iconSizeMedium),
                painter = painterResource(icon),
                contentDescription = null,

            )
        }
    }
}

@Composable
private fun ExpiredLabel(
    expired: Boolean,
    label: String
) {
    Card (
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, getExpiredBorderColor(expired))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(getExpiredContainerColor(expired))
                .padding(horizontal = Dimens.spacingSmallMedium),
        ) {
            Text(
                text = label,
                fontSize = Dimens.textSizeSmall,
                fontWeight = Bold,
                color = getExpiredLabelColor(expired)
            )
        }
    }
}

private fun getExpiredBorderColor(expired: Boolean) = if (expired) {
    errorDark
} else {
    inversePrimaryLight
}

private fun getExpiredContainerColor(expired: Boolean) = if (expired) {
    errorContainerLight
} else {
    primaryContainerLight
}

private fun getExpiredLabelColor(expired: Boolean) = if (expired) {
    errorLight
} else {
    tertiaryLight
}

data class ShelfItem(
    val id: Int,
    val productName: String,
    val productImage: DrawableResource,
    val expiryText: String,
    val expired: Boolean,
    val durationRemaining: String
)

@Preview
@Composable
private fun ShelfItemPreview() {
    val item = ShelfItem(
        id = 1,
        productName = "Milk",
        productImage = Res.drawable.vegetable,
        expiryText = "Expires Nov 4",
        expired = false,
        durationRemaining = "2 days"
    )
    MaterialTheme {
        ShelfItem(shelfItem = item)
    }
}