package com.simi.shelfpal.data.models

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ProductBasicInfo(
    val name: String = "",
    val category: String = ""
)

class ProductBasicInfoConverter {

    @TypeConverter
    fun fromString(productBasicInfo: String) = Json.decodeFromString<ProductBasicInfo>(productBasicInfo)

    @TypeConverter
    fun toString(productBasicInfo: ProductBasicInfo) = Json.encodeToString(productBasicInfo)
}