package com.simi.shelfpal.data.models

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ProductExpiryInfo(
    val expiryDay: Int? = null,
    val expiryMonth: Int? = null,
    val expiryYear: Int? = null,
    val hasDay: Boolean = false
)

class ProductExpiryInfoConverter {

    @TypeConverter
    fun fromString(productExpiryInfo: String) = Json.decodeFromString<ProductExpiryInfo>(productExpiryInfo)

    @TypeConverter
    fun toString(productExpiryInfo: ProductExpiryInfo) = Json.encodeToString(productExpiryInfo)
}