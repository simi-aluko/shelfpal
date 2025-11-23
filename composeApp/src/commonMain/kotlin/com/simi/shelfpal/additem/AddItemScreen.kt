package com.simi.shelfpal.additem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.simi.shelfpal.additem.AddItemBottomSheetType.CATEGORY
import com.simi.shelfpal.additem.AddItemBottomSheetType.DAY
import com.simi.shelfpal.additem.AddItemBottomSheetType.MONTH
import com.simi.shelfpal.additem.AddItemBottomSheetType.YEAR
import com.simi.shelfpal.additem.composables.AddItemBottomSheet
import com.simi.shelfpal.additem.composables.BasicInfoForm
import com.simi.shelfpal.additem.composables.BottomCTA
import com.simi.shelfpal.additem.composables.ExpiryForm
import com.simi.shelfpal.shared.TopBar
import com.simi.shelfpal.theme.Dimens
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.camera
import shelfpal.composeapp.generated.resources.categories
import shelfpal.composeapp.generated.resources.close
import shelfpal.composeapp.generated.resources.select_expiry_day
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.select_product_category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    navigateBack: () -> Unit,
    navigateToImageCapture: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetItems by remember { mutableStateOf(listOf<String>())}
    var bottomSheetType by remember { mutableStateOf(CATEGORY) }
    var bottomSheetTitle by remember { mutableStateOf("") }

    val dayBottomSheetTitle = stringResource(Res.string.select_expiry_day)
    val categoryBottomSheetTitle = stringResource(Res.string.select_product_category)
    val monthBottomSheetTitle = stringResource(Res.string.select_expiry_month)
    val yearBottomSheetTitle = stringResource(Res.string.select_expiry_year)
    val categories = stringArrayResource(Res.array.categories)

    var selectedDay by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.add_item),
                actionIcon = Res.drawable.close,
                actionIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        horizontal = Dimens.spacingMedium,
                        vertical = Dimens.spacingLarge
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                BasicInfoForm(
                    selectedCategory = selectedCategory,
                    onCategoryClick = {
                        bottomSheetType = CATEGORY
                        bottomSheetTitle = categoryBottomSheetTitle
                        bottomSheetItems = categories
                        showBottomSheet = true
                    },
                    onCameraClick = navigateToImageCapture
                )
                ExpiryForm(
                    expiryDay = selectedDay,
                    expiryMonth = selectedMonth,
                    expiryYear = selectedYear,
                    onExpiryMonthClick = {
                        bottomSheetType = MONTH
                        bottomSheetTitle = monthBottomSheetTitle
                        bottomSheetItems = Month.entries.map { it.name }
                        showBottomSheet = true
                    },
                    onExpiryYearClick = {
                        bottomSheetType = YEAR
                        bottomSheetTitle = yearBottomSheetTitle
                        bottomSheetItems = (2023..2030).map { it.toString() }
                        showBottomSheet = true
                    },
                    onExpiryDayClick = {
                        bottomSheetType = DAY
                        bottomSheetTitle = dayBottomSheetTitle
                        bottomSheetItems = (1..31).map { it.toString() }
                        showBottomSheet = true
                    },
                    onCameraClick = navigateToImageCapture
                )
                AddItemBottomSheet(
                    bottomSheetTitle = bottomSheetTitle,
                    showBottomSheet = showBottomSheet,
                    items = bottomSheetItems,
                    onItemSelected = {
                        when(bottomSheetType) {
                            MONTH -> selectedMonth = it
                            YEAR -> selectedYear = it
                            CATEGORY -> selectedCategory = it
                            DAY -> selectedDay = it
                        }
                        showBottomSheet = false
                    },
                    onDismiss = { showBottomSheet = false }
                )
                Spacer(modifier = Modifier.weight(1f))
                BottomCTA(
                    onSaveClick = {},
                    onCancelClick = navigateBack
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddItemScreenPreview() {
    MaterialTheme {
        AddItemScreen(
            navigateBack = {},
            navigateToImageCapture = {}
        )
    }
}

enum class AddItemBottomSheetType {
    CATEGORY, DAY, MONTH, YEAR
}
