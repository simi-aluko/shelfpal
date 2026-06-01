package com.simi.shelfpal.ui.addEditItem

import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.getBasicInfoPrompt
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType.CATEGORY
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType.DAY
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType.MONTH
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType.YEAR
import com.simi.shelfpal.ui.addEditItem.CameraEntry.BASIC_INFO
import com.simi.shelfpal.ui.addEditItem.CameraEntry.EXPIRY_INFO
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemImage
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemBasicInfo
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemBottomSheet
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemExpiryInfo
import com.simi.shelfpal.ui.addEditItem.models.ViewMetaData
import com.simi.shelfpal.ui.addEditItem.repository.AddItemRepository
import com.simi.shelfpal.ui.addEditItem.utils.getSelectableYearList
import com.simi.shelfpal.ui.addEditItem.utils.getShelPalItemFromViewModel
import com.simi.shelfpal.ui.addEditItem.utils.toEditItemBasicInfo
import com.simi.shelfpal.ui.addEditItem.utils.toEditItemExpiryInfo
import com.simi.shelfpal.ui.addEditItem.utils.validateBasicInfo
import com.simi.shelfpal.ui.addEditItem.utils.validateExpiryInfo
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getDisplayMonths
import com.simi.shelfpal.utils.getLocalTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.getStringArray
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.categories
import shelfpal.composeapp.generated.resources.edit_item
import shelfpal.composeapp.generated.resources.extracting_image_info
import shelfpal.composeapp.generated.resources.failed_to_extract_image_info
import shelfpal.composeapp.generated.resources.failed_to_save_item
import shelfpal.composeapp.generated.resources.loading_please_wait
import shelfpal.composeapp.generated.resources.select_expiry_day
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.select_product_category

class AddEditItemViewModel(
    private val viewMetaData: ViewMetaData
): ViewModel(), KoinComponent {

    sealed class ScreenState {
        object Idle : ScreenState()
        data class Loading(val title: StringResource) : ScreenState()
        object SaveSuccess : ScreenState()
        data class Error(val message: StringResource) : ScreenState()
    }

    val firebaseConnector by inject<FirebaseConnector>()
    val repository by inject<AddItemRepository>()

    private val _bottomSheet = MutableStateFlow<AddEditItemBottomSheet?>(null)
    val bottomSheet = _bottomSheet.asStateFlow()

    private val _addEditItemBasicInfo = MutableStateFlow(AddEditItemBasicInfo())
    val basicInfo = _addEditItemBasicInfo.asStateFlow()

    private val _addEditItemExpiryInfo = MutableStateFlow(AddEditItemExpiryInfo())
    val expiryInfo = _addEditItemExpiryInfo.asStateFlow()

    private val _screenTitle = MutableStateFlow("")
    val screenTitle = _screenTitle.asStateFlow()

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val screenState = _screenState.asStateFlow()

    suspend fun init() {
        viewMetaData.categories = getStringArray(Res.array.categories)
        _screenTitle.value = getString(Res.string.add_item)

        viewMetaData.itemId?.let {
            repository.getItem(it).collect { shelfPalItem ->
                _addEditItemBasicInfo.value = shelfPalItem?.toEditItemBasicInfo()
                    ?: AddEditItemBasicInfo()
                _addEditItemExpiryInfo.value = shelfPalItem?.toEditItemExpiryInfo()
                    ?: AddEditItemExpiryInfo()

                shelfPalItem?.let {
                    _screenTitle.value = getString(Res.string.edit_item)
                }
            }
        }
    }

    fun processImageCaptureResult(data: ByteArray) {
        when (viewMetaData.cameraEntry) {
            BASIC_INFO -> {
                getBasicInfoFromImage(imageData = data)
            }
            EXPIRY_INFO -> {
                getExpiryInfoFromImage(data)
            }
            null -> Unit
        }
    }

    fun onCategoryClick() {
        _bottomSheet.value = AddEditItemBottomSheet(
            showBottomSheet = true,
            title = Res.string.select_product_category,
            items = viewMetaData.categories
        )
        viewMetaData.bottomSheetType = CATEGORY
    }

    fun onExpiryDateClick(
        type: AddItemBottomSheetType
    ) {

        val (title, items) = when(type) {
            DAY -> {
                viewMetaData.bottomSheetType = DAY
                Pair(Res.string.select_expiry_day,(1..31).map { it.toString() })
            }
            MONTH -> {
                viewMetaData.bottomSheetType = MONTH
                Pair(Res.string.select_expiry_month, getDisplayMonths())
            }
            YEAR -> {
                viewMetaData.bottomSheetType = YEAR
                Pair(Res.string.select_expiry_year, getSelectableYearList())
            }
            CATEGORY -> {
                viewMetaData.bottomSheetType = CATEGORY
                Pair(Res.string.select_product_category, listOf())
            }
        }
        _bottomSheet.value = AddEditItemBottomSheet(
            showBottomSheet = true,
            title = title,
            items = items
        )
    }

    fun onBottomSheetDismiss() {
        _bottomSheet.value = _bottomSheet.value?.copy(
            showBottomSheet = false
        )
    }

    fun onBottomSheetItemSelected(item: String) {
        onBottomSheetDismiss()
        viewMetaData.bottomSheetType?.let {
            when (it) {
                MONTH -> {
                    _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
                        month = item
                    )
                }

                YEAR -> {
                    _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
                        year = item
                    )

                }

                CATEGORY -> {
                    _addEditItemBasicInfo.value = _addEditItemBasicInfo.value.copy(
                        category = item
                    )
                }

                DAY -> {
                    _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
                        day = item
                    )
                }
            }
        }
        if (viewMetaData.isItemValid == false) {
            validateItem()
        }
    }

    fun onNameChange(name: String) {
        _addEditItemBasicInfo.value = _addEditItemBasicInfo.value.copy(
            productName = name
        )
        if (viewMetaData.isItemValid == false) {
            validateItem()
        }
    }

    fun onDayChecked(checked: Boolean) {
        viewMetaData.hasDay = checked
        _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
            hasDay = viewMetaData.hasDay
        )
    }

    fun saveItem() {
        validateItem()
        if (viewMetaData.isItemValid == true) {
            viewModelScope.launch {
                try {
                    _screenState.value = ScreenState.Loading(Res.string.loading_please_wait)
                    val expiryInfoUrl = viewMetaData.expiryImage?.let {
                        repository.uploadImage(firebaseConnector.getFirebaseData(it), "expiry_image_${getLocalTimestamp()}")
                    }
                    val basicInfoUrl = viewMetaData.basicInfoImage?.let {
                        repository.uploadImage(firebaseConnector.getFirebaseData(it), "basic_info_image_${getLocalTimestamp()}")
                    }

                    val shelfPalItem = getShelPalItemFromViewModel(
                        addEditItemBasicInfo = _addEditItemBasicInfo.value,
                        addEditItemExpiryInfo = _addEditItemExpiryInfo.value,
                        basicInfoImageUrl = basicInfoUrl,
                        expiryInfoImageUrl = expiryInfoUrl
                    )

                    if (viewMetaData.itemId != null) {
                        val updatedItem = shelfPalItem.copy(id = viewMetaData.itemId)
                        repository.updateItem(updatedItem)
                    } else {
                        repository.saveItem(shelfPalItem)
                    }
                    _screenState.value = ScreenState.SaveSuccess
                } catch (e: Exception) {
                    e.printStackTrace()
                    _screenState.value = ScreenState.Error(Res.string.failed_to_save_item)
                }
            }
        }
    }

    fun clearError() {
        _screenState.value = ScreenState.Idle
    }

    fun updateCameraEntry(cameraEntry: CameraEntry) {
        viewMetaData.cameraEntry = cameraEntry
    }

    private fun getBasicInfoFromImage(imageData: ByteArray) {
        viewModelScope.launch {
            try {
                _screenState.value = ScreenState.Loading(Res.string.extracting_image_info)
                val response = firebaseConnector.getProductBasicInfoFromImage(
                    image = imageData,
                    prompt = getBasicInfoPrompt(viewMetaData.categories)
                )
                if (response != null) {
                    val info = Json.decodeFromString<ProductBasicInfo>(response)
                    viewMetaData.basicInfoImage = imageData

                    _addEditItemBasicInfo.value = _addEditItemBasicInfo.value.copy(
                        productName = info.name,
                        category = info.category,
                        image = AddEditItemImage(
                            bitmap = viewMetaData.basicInfoImage?.decodeToImageBitmap(),
                            isEdit = false
                        )
                    )
                    _screenState.value = ScreenState.Idle
                } else {
                    throw Exception("Null product basic info response from FirebaseAI")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = ScreenState.Error(Res.string.failed_to_extract_image_info)
            }
        }
    }

    private fun getExpiryInfoFromImage(
        imageData: ByteArray
    ) {
        viewModelScope.launch {
            try {
                _screenState.value = ScreenState.Loading(Res.string.extracting_image_info)
                val response = firebaseConnector.getProductExpiryInfoFromImage(
                    image = imageData
                )
                if (response != null) {
                    viewMetaData.expiryImage = imageData
                    val info = Json.decodeFromString<ProductExpiryInfo>(response)
                    val day = info.expiryDay
                    val month = info.expiryMonth
                    val year = info.expiryYear

                    _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
                        day = day.toString().takeIf {
                            viewMetaData.hasDay && day != null
                        } ?: "",
                        month = if (month != null && month in 1..12) {
                            Month.entries[month - 1].name.capitalizeFirstChar()
                        } else {
                            ""
                        },
                        year = year.toString().takeIf { year != null } ?: "",
                        image = AddEditItemImage(
                            bitmap = viewMetaData.expiryImage?.decodeToImageBitmap(),
                            isEdit = false
                        )
                    )
                    _screenState.value = ScreenState.Idle
                } else {
                    throw Exception("Null product expiry info response from FirebaseAI")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _screenState.value = ScreenState.Error(Res.string.failed_to_extract_image_info)
            }
        }
    }

    private fun validateItem() {
        val expiryInfoError = validateExpiryInfo(_addEditItemExpiryInfo.value)
        val basicInfoError = validateBasicInfo(_addEditItemBasicInfo.value, viewMetaData.categories)

        _addEditItemBasicInfo.value = _addEditItemBasicInfo.value.copy(
            basicInfoError = basicInfoError
        )

        _addEditItemExpiryInfo.value = _addEditItemExpiryInfo.value.copy(
            expiryInfoError = expiryInfoError
        )

        viewMetaData.isItemValid = !expiryInfoError.hasError && !basicInfoError.hasError
    }
}