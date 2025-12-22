package com.simi.shelfpal.ui.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.home.models.HomeShelfItem
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.errorContainerLight
import com.simi.shelfpal.ui.theme.errorDark
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.inversePrimaryLight
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.outlineVariantLight
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.secondaryLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.vegetable

@Composable
fun ShelfItems(
    modifier: Modifier = Modifier,
    items: List<HomeShelfItem>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        items(items.size) { index ->
            ShelfItem(homeShelfItem = items[index])
        }
    }
}

@Composable
private fun ShelfItem(
    homeShelfItem: HomeShelfItem
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
                expired = homeShelfItem.expired,
                icon = homeShelfItem.productImage
            )
            Spacer(modifier = Modifier.width(Dimens.spacingSmall))
            ProductInfo(
                productName = homeShelfItem.productName,
                expiryInfo = homeShelfItem.expiryText
            )
            Spacer(modifier = Modifier.weight(1f))
            ExpiredLabel(
                expired = homeShelfItem.expired,
                label = homeShelfItem.durationRemaining
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
        border = BorderStroke(1.dp,
            getExpiredBorderColor(expired)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    getExpiredContainerColor(
                        expired
                    )
                ),
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
        border = BorderStroke(1.dp,
            getExpiredBorderColor(expired)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = androidx.compose.ui.Modifier.Companion
                .background(
                    getExpiredContainerColor(
                        expired
                    )
                )
                .padding(horizontal = Dimens.spacingSmallMedium),
        ) {
            Text(
                text = label,
                fontSize = Dimens.textSizeSmall,
                fontWeight = Bold,
                color = getExpiredLabelColor(
                    expired
                )
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

@Preview
@Composable
private fun ShelfItemPreview() {
    val item = HomeShelfItem(
        id = 1,
        productName = "Milk",
        productImage = Res.drawable.vegetable,
        expiryText = "Expires Nov 4",
        expired = false,
        durationRemaining = "2 days"
    )
    MaterialTheme {
        ShelfItem(homeShelfItem = item)
    }
}