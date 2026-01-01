package com.simi.shelfpal.ui.additem

import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.getBasicInfoPrompt
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.additem.AddItemBottomSheetType.CATEGORY
import com.simi.shelfpal.ui.additem.AddItemBottomSheetType.DAY
import com.simi.shelfpal.ui.additem.AddItemBottomSheetType.MONTH
import com.simi.shelfpal.ui.additem.AddItemBottomSheetType.YEAR
import com.simi.shelfpal.ui.additem.CameraEntry.BASIC_INFO
import com.simi.shelfpal.ui.additem.CameraEntry.EXPIRY_INFO
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemBasicInfo
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemBottomSheet
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemExpiryInfo
import com.simi.shelfpal.ui.additem.models.ViewMetaData
import com.simi.shelfpal.ui.additem.repository.AddItemRepository
import com.simi.shelfpal.ui.additem.utils.getSelectableYearList
import com.simi.shelfpal.ui.additem.utils.getShelPalItemFromViewModel
import com.simi.shelfpal.ui.additem.utils.toAddEditItemBasicInfo
import com.simi.shelfpal.ui.additem.utils.toAddEditItemExpiryInfo
import com.simi.shelfpal.ui.additem.utils.validateBasicInfo
import com.simi.shelfpal.ui.additem.utils.validateExpiryInfo
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.getStringArray
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.categories
import shelfpal.composeapp.generated.resources.edit_item
import shelfpal.composeapp.generated.resources.select_expiry_day
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.select_product_category

class AddEditItemViewModel(
    private val viewMetaData: ViewMetaData
): ViewModel(), KoinComponent {

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

    suspend fun init() {
        viewMetaData.categories = getStringArray(Res.array.categories)
        _screenTitle.value = getString(Res.string.add_item)

        viewMetaData.itemId?.let {
            repository.getItem(it).collect { shelfPalItem ->
                _addEditItemBasicInfo.value = shelfPalItem?.basicInfo?.toAddEditItemBasicInfo()
                    ?: AddEditItemBasicInfo()
                _addEditItemExpiryInfo.value = shelfPalItem?.expiryInfo?.toAddEditItemExpiryInfo()
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
                // if images are present, upload to cloud storage
                // upon completion, save url to database
                // handle errors with try/catch
                // show error SnackBar

                val shelfPalItem = getShelPalItemFromViewModel(
                    addEditItemBasicInfo = _addEditItemBasicInfo.value,
                    addEditItemExpiryInfo = _addEditItemExpiryInfo.value
                )

                if (viewMetaData.itemId != null) {
                    repository.getItem(viewMetaData.itemId).collect { currentItem ->
                        currentItem?.let {
                            repository.updateItem(
                                currentItem.copy(
                                    basicInfo = shelfPalItem.basicInfo,
                                    expiryInfo = shelfPalItem.expiryInfo
                                )
                            )
                        }
                    }
                } else {
                    repository.saveItem(shelfPalItem)
                }
            }
        }
    }

    fun updateCameraEntry(cameraEntry: CameraEntry) {
        viewMetaData.cameraEntry = cameraEntry
    }

    private fun getBasicInfoFromImage(imageData: ByteArray) {
        viewModelScope.launch {
            try {
                val response = firebaseConnector.getProductBasicInfoFromImage(
                    image = imageData,
                    prompt = getBasicInfoPrompt(viewMetaData.categories)
                )
                if (response != null) {
                    val info = Json.decodeFromString<ProductBasicInfo>(response)

                    _addEditItemBasicInfo.value = _addEditItemBasicInfo.value.copy(
                        productName = info.name,
                        category = info.category,
                        imageBitmap = imageData.decodeToImageBitmap()
                    )
                } else {
                    throw Exception("Null product basic info response from FirebaseAI")
                }
            } catch (e: Exception) {
                // todo: send error state
                e.printStackTrace()
            }
        }
    }

    private fun getExpiryInfoFromImage(
        imageData: ByteArray
    ) {
        viewModelScope.launch {
            try {
                val response = firebaseConnector.getProductExpiryInfoFromImage(
                    image = imageData
                )
                if (response != null) {
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
                        imageBitmap = imageData.decodeToImageBitmap()
                    )
                } else {
                    throw Exception("Null product expiry info response from FirebaseAI")
                }
            } catch (e: Exception) {
                // todo: send error state
                e.printStackTrace()
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