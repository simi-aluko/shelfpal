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
import com.simi.shelfpal.ui.additem.models.AddItemViewState.AddItemBottomSheet
import com.simi.shelfpal.ui.additem.models.AddItemViewState.BasicInfo
import com.simi.shelfpal.ui.additem.models.AddItemViewState.ExpiryInfo
import com.simi.shelfpal.ui.additem.models.ViewMetaData
import com.simi.shelfpal.ui.additem.repository.AddItemRepository
import com.simi.shelfpal.ui.additem.utils.getSelectableYearList
import com.simi.shelfpal.ui.additem.utils.getShelPalItemFromViewModel
import com.simi.shelfpal.ui.additem.utils.validateBasicInfo
import com.simi.shelfpal.ui.additem.utils.validateExpiryInfo
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.select_expiry_day
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.select_product_category

class AddItemViewModel(
    val viewMetaData: ViewMetaData = ViewMetaData()
): ViewModel(), KoinComponent {
    val firebaseConnector by inject<FirebaseConnector>()
    val repository by inject<AddItemRepository>()

    private val _bottomSheet = MutableStateFlow<AddItemBottomSheet?>(null)
    val bottomSheet = _bottomSheet.asStateFlow()

    private val _basicInfo = MutableStateFlow(BasicInfo())
    val basicInfo = _basicInfo.asStateFlow()

    private val _expiryInfo = MutableStateFlow(ExpiryInfo())
    val expiryInfo = _expiryInfo.asStateFlow()

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
        _bottomSheet.value = AddItemBottomSheet(
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
        _bottomSheet.value = AddItemBottomSheet(
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
                    _expiryInfo.value = _expiryInfo.value.copy(
                        month = item
                    )
                }

                YEAR -> {
                    _expiryInfo.value = _expiryInfo.value.copy(
                        year = item
                    )

                }

                CATEGORY -> {
                    _basicInfo.value = _basicInfo.value.copy(
                        category = item
                    )
                }

                DAY -> {
                    _expiryInfo.value = _expiryInfo.value.copy(
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
        _basicInfo.value = _basicInfo.value.copy(
            productName = name
        )
        if (viewMetaData.isItemValid == false) {
            validateItem()
        }
    }

    fun onDayChecked(checked: Boolean) {
        viewMetaData.hasDay = checked
        _expiryInfo.value = _expiryInfo.value.copy(
            hasDay = viewMetaData.hasDay
        )
    }

    fun saveItem() {
        validateItem()
        if (viewMetaData.isItemValid == true) {
            viewModelScope.launch {
                // if images are present, upload to cloud storage
                // upon completion, save to database
                // handle errors with try/catch
                // show error SnackBar
                repository.saveItem(
                    getShelPalItemFromViewModel(
                        basicInfo = _basicInfo.value,
                        expiryInfo = _expiryInfo.value
                    )
                )
            }
        }
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

                    _basicInfo.value = _basicInfo.value.copy(
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

                    _expiryInfo.value = _expiryInfo.value.copy(
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
        val expiryInfoError = validateExpiryInfo(_expiryInfo.value)
        val basicInfoError = validateBasicInfo(_basicInfo.value, viewMetaData.categories)

        _basicInfo.value = _basicInfo.value.copy(
            basicInfoError = basicInfoError
        )

        _expiryInfo.value = _expiryInfo.value.copy(
            expiryInfoError = expiryInfoError
        )

        viewMetaData.isItemValid = !expiryInfoError.hasError && !basicInfoError.hasError
    }
}