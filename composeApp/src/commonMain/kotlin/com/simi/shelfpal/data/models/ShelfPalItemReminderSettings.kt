package com.simi.shelfpal.data.models

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ShelfPalItemReminderSettings(
    val notifyMonthBefore: Boolean = false,
    val notifyWeekBefore: Boolean = false,
    val notifyDayBefore: Boolean = false,
    val notifyOnDay: Boolean = false,
    val customDaysBefore: Int? = null
)

class ShelfPalItemReminderSettingsConverter {

    @TypeConverter
    fun fromShelfPalItemReminderSettings(
        settings: ShelfPalItemReminderSettings
    ) = Json.encodeToString(settings)

    @TypeConverter
    fun toShelfPalItemReminderSettings(
        settings: String
    ) = Json.decodeFromString<ShelfPalItemReminderSettings>(settings)
}

