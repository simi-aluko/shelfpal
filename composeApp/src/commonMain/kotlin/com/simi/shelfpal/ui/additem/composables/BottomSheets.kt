package com.simi.shelfpal.ui.additem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
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
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.select_expiry_month

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
                    _root_ide_package_.com.simi.shelfpal.ui.home.composables.FilterItemCard(
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
        _root_ide_package_.com.simi.shelfpal.ui.additem.composables.AddItemBottomSheet(
            bottomSheetTitle = stringResource(Res.string.select_expiry_month),
            showBottomSheet = true,
            items = Month.entries.map { it.name },
            onItemSelected = {},
            onDismiss = {}
        )
    }
}