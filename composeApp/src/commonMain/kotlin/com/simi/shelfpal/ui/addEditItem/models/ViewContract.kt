package com.simi.shelfpal.ui.addEditItem.models

import androidx.compose.ui.graphics.ImageBitmap
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType
import com.simi.shelfpal.ui.addEditItem.CameraEntry
import org.jetbrains.compose.resources.StringResource

sealed class AddEditItemViewState {

    data class AddEditItemBasicInfo(
        val image: AddEditItemImage = AddEditItemImage(),
        val productName: String = "",
        val category: String = "",
        val basicInfoError: BasicInfoError = BasicInfoError(),
    ) : AddEditItemViewState()

    data class AddEditItemExpiryInfo(
        val day: String = "",
        val month: String = "",
        val year: String = "",
        val hasDay: Boolean = false,
        val image: AddEditItemImage = AddEditItemImage(),
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
    var categories: List<String> = listOf(),
    var expiryImage: ByteArray? = null,
    var basicInfoImage: ByteArray? = null,
    var originalItem: ShelfPalItem? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ViewMetaData

        if (itemId != other.itemId) return false
        if (isItemValid != other.isItemValid) return false
        if (hasDay != other.hasDay) return false
        if (cameraEntry != other.cameraEntry) return false
        if (bottomSheetType != other.bottomSheetType) return false
        if (categories != other.categories) return false
        if (!expiryImage.contentEquals(other.expiryImage)) return false
        if (!basicInfoImage.contentEquals(other.basicInfoImage)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = itemId?.hashCode() ?: 0
        result = 31 * result + (isItemValid?.hashCode() ?: 0)
        result = 31 * result + hasDay.hashCode()
        result = 31 * result + (cameraEntry?.hashCode() ?: 0)
        result = 31 * result + (bottomSheetType?.hashCode() ?: 0)
        result = 31 * result + categories.hashCode()
        result = 31 * result + (expiryImage?.contentHashCode() ?: 0)
        result = 31 * result + (basicInfoImage?.contentHashCode() ?: 0)
        return result
    }
}

data class AddEditItemImage(
    val bitmap: ImageBitmap? = null,
    val url: String? = null,
    val isEdit: Boolean = false
)