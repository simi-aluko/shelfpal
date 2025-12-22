package com.simi.shelfpal.data.models

import androidx.room.TypeConverter
import com.simi.shelfpal.ui.shared.models.ProductCategory
import com.simi.shelfpal.ui.shared.models.ProductCategory.OTHER
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ProductBasicInfo(
    val productName: String = "",
    val productCategory: ProductCategory = OTHER
)

class ProductBasicInfoConverter {

    @TypeConverter
    fun fromString(productBasicInfo: String) = Json.decodeFromString<ProductBasicInfo>(productBasicInfo)

    @TypeConverter
    fun toString(productBasicInfo: ProductBasicInfo) = Json.encodeToString(productBasicInfo)
}