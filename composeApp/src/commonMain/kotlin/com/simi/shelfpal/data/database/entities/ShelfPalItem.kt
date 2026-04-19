package com.simi.shelfpal.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simi.shelfpal.data.database.shelfItemsTable
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettings

@Entity(
    tableName = shelfItemsTable
)
data class ShelfPalItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productImageUrl: String? = null,
    val productExpiryImageUrl: String? = null,
    val used: Boolean = false,
    @Embedded val basicInfo: ProductBasicInfo,
    @Embedded val expiryInfo: ProductExpiryInfo,
    @Embedded val reminderSettings: ShelfPalItemReminderSettings = ShelfPalItemReminderSettings()
)