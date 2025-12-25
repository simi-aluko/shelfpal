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
import androidx.compose.ui.draw.alpha
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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.apps
import shelfpal.composeapp.generated.resources.bread
import shelfpal.composeapp.generated.resources.category_all_categories
import shelfpal.composeapp.generated.resources.category_beverages
import shelfpal.composeapp.generated.resources.category_canned_foods_oils_sauces_and_spices
import shelfpal.composeapp.generated.resources.category_cleaning_and_household
import shelfpal.composeapp.generated.resources.category_fresh_fruits_and_vegetables
import shelfpal.composeapp.generated.resources.category_infant_and_pet_care
import shelfpal.composeapp.generated.resources.category_meats_dairy_and_frozen_foods
import shelfpal.composeapp.generated.resources.category_medications_and_vitamins
import shelfpal.composeapp.generated.resources.category_pastry_and_staples
import shelfpal.composeapp.generated.resources.category_personal_care_and_beauty
import shelfpal.composeapp.generated.resources.category_prepared_meals
import shelfpal.composeapp.generated.resources.cleaningtools
import shelfpal.composeapp.generated.resources.drinks
import shelfpal.composeapp.generated.resources.empty_home_screen
import shelfpal.composeapp.generated.resources.empty_home_screen_subtitle
import shelfpal.composeapp.generated.resources.food
import shelfpal.composeapp.generated.resources.medicine
import shelfpal.composeapp.generated.resources.personalcare
import shelfpal.composeapp.generated.resources.petcare
import shelfpal.composeapp.generated.resources.seafood
import shelfpal.composeapp.generated.resources.selectall
import shelfpal.composeapp.generated.resources.spices
import shelfpal.composeapp.generated.resources.surprise
import shelfpal.composeapp.generated.resources.vegetable

@Composable
fun ShelfItems(
    modifier: Modifier = Modifier,
    items: List<HomeShelfItem>
) {
    if (items.isEmpty()) {
        EmptyList()
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
        ) {
            items(items.size) { index ->
                ShelfItem(homeShelfItem = items[index])
            }
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
                icon = getProductCategoryImage(homeShelfItem.category)
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

@Composable
private fun EmptyList() {
    Column(
        modifier = Modifier.fillMaxSize().padding(Dimens.spacingMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(0.6f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.alpha(0.8f),
                painter = painterResource(Res.drawable.surprise),
                contentDescription = "Your shelf is empty"
            )
        }
        Box(
            modifier = Modifier.weight(0.4f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.empty_home_screen),
                    fontSize = Dimens.textSizeXLarge,
                    fontWeight = FontWeight.W400
                )
                Text(
                    text = stringResource(Res.string.empty_home_screen_subtitle),
                    fontSize = Dimens.textSizeSmall,
                    fontWeight = FontWeight.W300
                )
            }
        }
    }
}

@Composable
fun getProductCategoryImage(category: String) = when (category) {
    stringResource(Res.string.category_fresh_fruits_and_vegetables) -> Res.drawable.vegetable
    stringResource(Res.string.category_meats_dairy_and_frozen_foods) -> Res.drawable.food
    stringResource(Res.string.category_pastry_and_staples) -> Res.drawable.bread
    stringResource(Res.string.category_canned_foods_oils_sauces_and_spices) -> Res.drawable.spices
    stringResource(Res.string.category_beverages) -> Res.drawable.drinks
    stringResource(Res.string.category_prepared_meals) -> Res.drawable.seafood
    stringResource(Res.string.category_medications_and_vitamins) -> Res.drawable.medicine
    stringResource(Res.string.category_personal_care_and_beauty) -> Res.drawable.personalcare
    stringResource(Res.string.category_cleaning_and_household) -> Res.drawable.cleaningtools
    stringResource(Res.string.category_infant_and_pet_care) -> Res.drawable.petcare
    stringResource(Res.string.category_all_categories) -> Res.drawable.apps
    else -> Res.drawable.selectall
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
        category = stringResource(Res.string.category_fresh_fruits_and_vegetables),
        expiryText = "Expires Nov 4",
        expired = false,
        durationRemaining = "2 days",
        expiryYear = 2025
    )
    MaterialTheme {
        ShelfItem(homeShelfItem = item)
    }
}