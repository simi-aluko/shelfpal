package com.simi.shelfpal.ui.additem.models

import androidx.compose.ui.graphics.ImageBitmap
import com.simi.shelfpal.ui.additem.AddItemBottomSheetType
import com.simi.shelfpal.ui.additem.CameraEntry
import org.jetbrains.compose.resources.StringResource

sealed class AddEditItemViewState {

    data class AddEditItemBasicInfo(
        val imageBitmap: ImageBitmap? = null,
        val productName: String = "",
        val category: String = "",
        val basicInfoError: BasicInfoError = BasicInfoError(),
    ) : AddEditItemViewState()

    data class AddEditItemExpiryInfo(
        val day: String = "",
        val month: String = "",
        val year: String = "",
        val hasDay: Boolean = false,
        val imageBitmap: ImageBitmap? = null,
        val expiryInfoError: ExpiryInfoError = ExpiryInfoError()
    ) : AddEditItemViewState()

    data class AddEditItemBottomSheet(
        val showBottomSheet: Boolean,
        val title: StringResource,
        val items: List<String>
    ) : AddEditItemViewState()
}

data class ViewMetaData(
    val itemId: Long?,
    var isItemValid: Boolean? = null,
    var cameraEntry: CameraEntry? = null,
    var hasDay: Boolean = false,
    var bottomSheetType: AddItemBottomSheetType? = null,
    var categories: List<String> = listOf()
)