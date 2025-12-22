package com.simi.shelfpal.ui.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.home.models.Filter.Category
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.outlineVariantLight
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.scrimLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import com.simi.shelfpal.ui.theme.secondaryLight
import com.simi.shelfpal.ui.theme.surfaceContainerLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.all_items
import shelfpal.composeapp.generated.resources.apps
import shelfpal.composeapp.generated.resources.check_24px

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sheetState: SheetState,
    showBottomSheet: Boolean,
    bottomSheetHeaderViewModel: BottomSheetHeaderViewModel,
    filters: List<com.simi.shelfpal.ui.home.models.Filter>,
    onDismiss: () -> Unit = {}
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            BottomSheetHeader(
                bottomSheetHeaderViewModel = bottomSheetHeaderViewModel
            )
            Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
            BottomSheetContent(
                filters = filters
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetHeader(
    bottomSheetHeaderViewModel: BottomSheetHeaderViewModel
) {

    Column(
        modifier = Modifier.padding(Dimens.spacingSmall)
    ){
        Text(
            text = bottomSheetHeaderViewModel.title,
            fontWeight = FontWeight.Bold,
            color = primaryLight,
            fontSize = Dimens.textSizeLarge
        )
        Spacer(modifier = Modifier.size(Dimens.spacingMicro))
        Text(
            text = bottomSheetHeaderViewModel.subtitle,
            color = secondaryLight
        )
    }
}

@Composable
private fun BottomSheetContent(
    filters: List<com.simi.shelfpal.ui.home.models.Filter>
) {
    LazyColumn(
        modifier = Modifier.padding(Dimens.spacingSmall),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
    ) {
        items(filters.size) {
            when(val filter = filters[it]) {
                is Category -> _root_ide_package_.com.simi.shelfpal.ui.home.composables.CategoryFilterItem(
                    category = filter
                )
                is com.simi.shelfpal.ui.home.models.Filter.Month -> _root_ide_package_.com.simi.shelfpal.ui.home.composables.MonthFilterItem(
                    month = filter
                )
                is com.simi.shelfpal.ui.home.models.Filter.Year -> _root_ide_package_.com.simi.shelfpal.ui.home.composables.YearFilterItem(
                    year = filter
                )
            }
        }
    }
}

@Composable
private fun CategoryFilterItem(
    category: Category
) {
    _root_ide_package_.com.simi.shelfpal.ui.home.composables.FilterItemCard(
        selected = category.selected,
        leftIcon = category.leftIcon,
        title = category.title,
        rightIcon = category.rightIcon,
        onClick = category.onClick
    )
}

@Composable
private fun MonthFilterItem(
    month: com.simi.shelfpal.ui.home.models.Filter.Month
) {
    _root_ide_package_.com.simi.shelfpal.ui.home.composables.FilterItemCard(
        selected = month.selected,
        leftIcon = null,
        title = month.title,
        rightIcon = month.rightIcon,
        onClick = month.onClick
    )
}

@Composable
private fun YearFilterItem(
    year: com.simi.shelfpal.ui.home.models.Filter.Year
) {
    _root_ide_package_.com.simi.shelfpal.ui.home.composables.FilterItemCard(
        selected = year.selected,
        leftIcon = null,
        title = year.title,
        rightIcon = year.rightIcon,
        onClick = year.onClick
    )
}

@Composable
fun FilterItemCard(
    selected: Boolean,
    leftIcon: DrawableResource?,
    title: String,
    rightIcon: DrawableResource?,
    onClick: () -> Unit = {}
) {

    val outlineColor = if (selected) {
        primaryLight
    } else {
        outlineVariantLight
    }

    val containerColor = if (selected) {
        secondaryContainerLight
    } else {
        surfaceContainerLight
    }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(1.dp, outlineColor),
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(Dimens.spacingSmall)
        ) {
            leftIcon?.let {
                FilterItemIcon(
                    isSelected = selected,
                    icon = it
                )
            }
            Spacer(modifier = Modifier.width(Dimens.spacingSmall))
            Text(
                text = title,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.weight(1f))
            rightIcon?.let {
                Icon(
                    imageVector = vectorResource(it),
                    contentDescription = null,
                    tint = scrimLight
                )
            }
        }
    }
}

@Composable
private fun FilterItemIcon(
    isSelected: Boolean,
    icon: DrawableResource
) {
    val selectedItemIconBorder = if (isSelected) {
        BorderStroke(1.dp, outlineVariantLight)
    } else {
        null
    }

    Card (
        shape = CircleShape,
        modifier = Modifier.size(Dimens.iconSizeLarge),
        border = selectedItemIconBorder
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(primaryContainerLight),
        ) {
            Image(
                modifier = Modifier.height(Dimens.iconSizeMedium),
                painter = painterResource(icon),
                contentDescription = null,
            )
        }
    }
}

data class BottomSheetHeaderViewModel(
    val title: String,
    val subtitle: String
)

@OptIn(InternalResourceApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CategoryFilterPreview() {
    val filters = listOf(
        Category(
            title = stringResource(Res.string.all_items),
            leftIcon = Res.drawable.apps,
            selected = true,
            rightIcon = Res.drawable.check_24px
        ),
        Category(
            title = stringResource(Res.string.all_items),
            leftIcon = Res.drawable.apps,
            selected = false,
            rightIcon = null
        ),
        Category(
            title = stringResource(Res.string.all_items),
            leftIcon = Res.drawable.apps,
            selected = false,
            rightIcon = null
        )
    )

    MaterialTheme {
        FilterBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            showBottomSheet = true,
            bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
                title = "Filter by category",
                subtitle = "Choose a category to filter your items"
            ),
            filters = filters
        )
    }
}