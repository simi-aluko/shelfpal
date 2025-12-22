package com.simi.shelfpal.ui.additem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simi.shelfpal.ui.additem.CameraEntry.*
import com.simi.shelfpal.ui.additem.composables.BasicInfoForm
import com.simi.shelfpal.ui.additem.composables.BottomCTA
import com.simi.shelfpal.ui.additem.composables.ExpiryForm
import com.simi.shelfpal.ui.shared.IMAGE_CAPTURE_RESULT
import com.simi.shelfpal.ui.shared.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_item
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
    navigateToImageCapture: () -> Unit,
    navController: NavController,
    viewModel: AddItemViewModel = viewModel { AddItemViewModel() }
) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val imageCaptureResult by savedStateHandle?.getStateFlow<ByteArray?>(IMAGE_CAPTURE_RESULT,  null)
        ?.collectAsState(initial = null) ?: mutableStateOf(null)
    var basicInfoImage by rememberSaveable { mutableStateOf<ByteArray?>(null) }
    var expiryInfoImage by rememberSaveable { mutableStateOf<ByteArray?>(null) }
    var cameraEntry by rememberSaveable { mutableStateOf<CameraEntry?>(null) }

    val aiProductBasicInfo by viewModel.aiProductBasicInfo.collectAsState()
    val aiProductExpiryInfo by viewModel.aiProductExpiryInfo.collectAsState()

    val categories = stringArrayResource(Res.array.categories)

    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetItems by remember { mutableStateOf(listOf<String>())}
    var bottomSheetType by remember { mutableStateOf(AddItemBottomSheetType.CATEGORY) }
    var bottomSheetTitle by remember { mutableStateOf("") }

    val dayBottomSheetTitle = stringResource(Res.string.select_expiry_day)
    val categoryBottomSheetTitle = stringResource(Res.string.select_product_category)
    val monthBottomSheetTitle = stringResource(Res.string.select_expiry_month)
    val yearBottomSheetTitle = stringResource(Res.string.select_expiry_year)

    var selectedCategory by rememberSaveable { mutableStateOf("") }
    var productName by rememberSaveable { mutableStateOf("") }

    var selectedDay by rememberSaveable { mutableStateOf("") }
    var selectedMonth by rememberSaveable { mutableStateOf("") }
    var selectedYear by rememberSaveable { mutableStateOf("") }
    var hasDay by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(imageCaptureResult) {
        val result = imageCaptureResult
        if (result != null) {
            when (cameraEntry) {
                BASIC_INFO -> {
                    basicInfoImage = result
                    viewModel.getBasicInfoFromImage(
                        image = result,
                        categories = categories
                    )
                }
                EXPIRY_INFO -> {
                    expiryInfoImage = result
                    viewModel.getExpiryInfoFromImage(result)
                }
                null -> Unit
            }
            savedStateHandle?.remove<ByteArray>(IMAGE_CAPTURE_RESULT)
        }
    }

    LaunchedEffect(aiProductBasicInfo) {
        if (aiProductBasicInfo.productName.isNotEmpty()) {
            productName = aiProductBasicInfo.productName
        }
        if (aiProductBasicInfo.productCategory.name.isNotEmpty()) {
            selectedCategory = aiProductBasicInfo.productCategory.name
        }
    }

    LaunchedEffect(aiProductExpiryInfo) {
        val expiryDay = aiProductExpiryInfo.expiryDay
        val expiryMonth = aiProductExpiryInfo.expiryMonth
        val expiryYear = aiProductExpiryInfo.expiryYear

        selectedDay = expiryDay.toString().takeIf { aiProductExpiryInfo.hasDay && expiryDay != null } ?: ""
        selectedYear = expiryYear.toString().takeIf { expiryYear != null } ?: ""

        selectedMonth = if (expiryMonth != null && expiryMonth in 1..12) {
            Month.entries[expiryMonth - 1].name
        } else {
            ""
        }
        hasDay = aiProductExpiryInfo.hasDay
    }

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
                    productName = productName,
                    selectedCategory = selectedCategory,
                    onCategoryClick = {
                        bottomSheetType =
                            AddItemBottomSheetType.CATEGORY
                        bottomSheetTitle = categoryBottomSheetTitle
                        bottomSheetItems = categories
                        showBottomSheet = true
                    },
                    onCameraClick = {
                        cameraEntry = BASIC_INFO
                        navigateToImageCapture()
                    },
                    imageBitmap = basicInfoImage?.decodeToImageBitmap()
                )
                Spacer(modifier = Modifier.height(Dimens.spacingLarge))
                ExpiryForm(
                    expiryDay = selectedDay,
                    expiryMonth = selectedMonth,
                    expiryYear = selectedYear,
                    onExpiryMonthClick = {
                        bottomSheetType =
                            AddItemBottomSheetType.MONTH
                        bottomSheetTitle = monthBottomSheetTitle
                        bottomSheetItems = Month.entries.map { it.name }
                        showBottomSheet = true
                    },
                    onExpiryYearClick = {
                        bottomSheetType =
                            AddItemBottomSheetType.YEAR
                        bottomSheetTitle = yearBottomSheetTitle
                        bottomSheetItems = (2023..2030).map { it.toString() }
                        showBottomSheet = true
                    },
                    onExpiryDayClick = {
                        bottomSheetType =
                            AddItemBottomSheetType.DAY
                        bottomSheetTitle = dayBottomSheetTitle
                        bottomSheetItems = (1..31).map { it.toString() }
                        showBottomSheet = true
                    },
                    onCameraClick = {
                        cameraEntry =
                            EXPIRY_INFO
                        navigateToImageCapture()
                    },
                    imageBitmap = expiryInfoImage?.decodeToImageBitmap(),
                    hasDay = hasDay
                )
                _root_ide_package_.com.simi.shelfpal.ui.additem.composables.AddItemBottomSheet(
                    bottomSheetTitle = bottomSheetTitle,
                    showBottomSheet = showBottomSheet,
                    items = bottomSheetItems,
                    onItemSelected = {
                        when (bottomSheetType) {
                            AddItemBottomSheetType.MONTH -> selectedMonth =
                                it

                            AddItemBottomSheetType.YEAR -> selectedYear =
                                it

                            AddItemBottomSheetType.CATEGORY -> selectedCategory =
                                it

                            AddItemBottomSheetType.DAY -> selectedDay =
                                it
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
            navigateToImageCapture = {},
            navController = rememberNavController()
        )
    }
}

enum class AddItemBottomSheetType {
    CATEGORY, DAY, MONTH, YEAR
}

enum class CameraEntry {
    BASIC_INFO,
    EXPIRY_INFO
}