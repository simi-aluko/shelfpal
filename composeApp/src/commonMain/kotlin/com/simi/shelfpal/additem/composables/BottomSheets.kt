package com.simi.shelfpal.additem.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.additem.AddItemBottomSheetType
import com.simi.shelfpal.additem.AddItemBottomSheetType.CATEGORY
import com.simi.shelfpal.additem.AddItemBottomSheetType.MONTH
import com.simi.shelfpal.home.composables.FilterItemCard
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.primaryLight
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.categories
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_product_category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemBottomSheet(
    bottomSheetTitle: String,
    showBottomSheet: Boolean,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Text(
                text = bottomSheetTitle,
                fontWeight = FontWeight.Bold,
                color = primaryLight,
                fontSize = Dimens.textSizeLarge,
                modifier = Modifier.padding(Dimens.spacingSmallMedium)
            )
            LazyColumn(
                modifier = Modifier.padding(Dimens.spacingSmallMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                items(items.size) { index ->
                    FilterItemCard(
                        selected = false,
                        rightIcon = null,
                        leftIcon = null,
                        title = items[index],
                        onClick = {
                            onItemSelected(items[index])
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddItemBottomSheetPreview() {
    MaterialTheme {
        AddItemBottomSheet(
            bottomSheetTitle = stringResource(Res.string.select_expiry_month),
            showBottomSheet = true,
            items = Month.entries.map { it.name },
            onItemSelected = {},
            onDismiss = {}
        )
    }
}