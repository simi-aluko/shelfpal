package com.simi.shelfpal.ui.addEditItem

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simi.shelfpal.ui.addEditItem.CameraEntry.BASIC_INFO
import com.simi.shelfpal.ui.addEditItem.CameraEntry.EXPIRY_INFO
import com.simi.shelfpal.ui.addEditItem.composables.AddItemBottomSheet
import com.simi.shelfpal.ui.addEditItem.composables.BasicInfoForm
import com.simi.shelfpal.ui.addEditItem.composables.BottomCTA
import com.simi.shelfpal.ui.addEditItem.composables.ExpiryForm
import com.simi.shelfpal.ui.addEditItem.models.ViewMetaData
import com.simi.shelfpal.ui.shared.IMAGE_CAPTURE_RESULT
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    itemId: Long?,
    navigateBack: () -> Unit,
    navigateToImageCapture: () -> Unit,
    navController: NavController,
    viewModel: AddEditItemViewModel = viewModel {
        AddEditItemViewModel(
            viewMetaData = ViewMetaData(itemId = itemId)
        )
    }
) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val imageCaptureResult by savedStateHandle?.getStateFlow<ByteArray?>(IMAGE_CAPTURE_RESULT,  null)
        ?.collectAsState(initial = null) ?: mutableStateOf(null)

    val basicInfo by viewModel.basicInfo.collectAsStateWithLifecycle()
    val expiryInfo by viewModel.expiryInfo.collectAsStateWithLifecycle()
    val bottomSheet by viewModel.bottomSheet.collectAsStateWithLifecycle()
    val screenTitle by viewModel.screenTitle.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    LaunchedEffect(imageCaptureResult) {
        val result = imageCaptureResult
        if (result != null) {
            viewModel.processImageCaptureResult(result)
            savedStateHandle?.remove<ByteArray>(IMAGE_CAPTURE_RESULT)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = screenTitle,
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
                        start = Dimens.spacingMedium,
                        end = Dimens.spacingMedium,
                        top = Dimens.spacingLarge
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                BasicInfoForm(
                    formData = basicInfo,
                    onCategoryClick = {
                        viewModel.onCategoryClick()
                    },
                    onCameraClick = {
                        viewModel.updateCameraEntry(BASIC_INFO)
                        navigateToImageCapture()
                    },
                    onNameChange = {
                        viewModel.onNameChange(it)
                    },
                )
                Spacer(modifier = Modifier.height(Dimens.spacingLarge))
                ExpiryForm(
                    formData = expiryInfo,
                    onExpiryDateClick = {
                        viewModel.onExpiryDateClick(it)
                    },
                    onDayChecked = {
                        viewModel.onDayChecked(it)
                    },
                    onCameraClick = {
                        viewModel.updateCameraEntry(EXPIRY_INFO)
                        navigateToImageCapture()
                    }
                )
                AddItemBottomSheet(
                    bottomSheetData = bottomSheet,
                    onItemSelected = {
                        viewModel.onBottomSheetItemSelected(it)
                    },
                    onDismiss = { viewModel.onBottomSheetDismiss()}
                )
                Spacer(modifier = Modifier.weight(1f))
                BottomCTA(
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.saveItem()
                            navigateBack()
                        }
                    },
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
        AddEditItemScreen(
            navigateBack = {},
            navigateToImageCapture = {},
            navController = rememberNavController(),
            itemId = 1L
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