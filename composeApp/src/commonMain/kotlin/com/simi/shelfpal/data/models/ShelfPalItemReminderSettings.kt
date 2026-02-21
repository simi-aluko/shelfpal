package com.simi.shelfpal.data.models

import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ShelfPalItemReminderSettings(
    val notifyMonthBefore: Boolean = true,
    val notifyWeekBefore: Boolean = true,
    val notifyDayBefore: Boolean = true,
    val notifyOnDay: Boolean = true,
    val customDaysBefore: Int? = null,
    val hasExpired: Boolean = false
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

