package com.simi.shelfpal.ui.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.home.models.Filter
import com.simi.shelfpal.ui.theme.Dimens
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.category_all_categories

@Composable
fun TopRowFilter(
    filters: Set<Filter>,
    onFilterClick: (Filter) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMicro),
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEach { item ->
            item {
                when (item) {
                    is Filter.Category -> CategoryFilter(
                        category = item,
                        onFilterClick = onFilterClick
                    )
                    is Filter.Month -> MonthFilter(
                        month = item,
                        onFilterClick = onFilterClick
                    )
                    is Filter.Year -> YearFilter(
                        year = item,
                        onFilterClick = onFilterClick
                    )
                    is Filter.ItemStatus -> ItemStatusFilter(
                        itemStatus = item,
                        onFilterClick = onFilterClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilter(
    category: Filter.Category,
    onFilterClick: (Filter.Category) -> Unit
) {
    FilterCard(
        title = category.title,
        leftIcon = getProductCategoryImage(category.title),
        rightIcon = category.rightIcon,
        onClick = {
            onFilterClick(category)
        }
    )
}

@Composable
private fun MonthFilter(
    month: Filter.Month,
    onFilterClick: (Filter.Month) -> Unit
) {
    FilterCard(
        title = month.title,
        leftIcon = null,
        rightIcon = month.rightIcon,
        onClick = {
            onFilterClick(month)
        }
    )
}

@Composable
private fun YearFilter(
    year: Filter.Year,
    onFilterClick: (Filter.Year) -> Unit
) {
    FilterCard(
        title = year.title,
        leftIcon = null,
        rightIcon = year.rightIcon,
        onClick = {
            onFilterClick(year)
        }
    )
}

@Composable
private fun ItemStatusFilter(
    itemStatus: Filter.ItemStatus,
    onFilterClick: (Filter.ItemStatus) -> Unit
) {
    FilterCard(
        title = itemStatus.title,
        leftIcon = itemStatus.leftIcon,
        rightIcon = itemStatus.rightIcon,
        onClick = {
            onFilterClick(itemStatus)
        }
    )
}

@Composable
private fun FilterCard(
    title: String,
    leftIcon: DrawableResource?,
    rightIcon: DrawableResource?,
    onClick: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(Dimens.radiusCircle),
        modifier = Modifier
            .padding(horizontal = Dimens.spacingExtraSmall)
            .clickable(
                enabled = true,
                onClick = onClick
            )
    ) {
        Row (
            modifier = Modifier.padding(horizontal = Dimens.spacingSmallMedium, vertical = Dimens.spacingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
        ) {
            leftIcon?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.height(Dimens.iconSizeSmall)
                )
            }
            Text(
                text = title,
                fontSize = Dimens.textSizeSmall,
                fontWeight = FontWeight.SemiBold
            )
            rightIcon?.let {
                Icon(
                    painter = painterResource(rightIcon),
                    contentDescription = null,
                    modifier = Modifier.height(Dimens.iconSizeSmall)
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeFilterPreview() {
    MaterialTheme {
        TopRowFilter(
            filters = setOf(
                Filter.Category(
                    title = stringResource(Res.string.category_all_categories),
                    onClick = { }
                ),
                Filter.Month(
                    title = "January",
                    onClick = { }
                ),
                Filter.Year(
                    title = "2025",
                    onClick = { }
                )
            ),
            onFilterClick = {}
        )
    }
}