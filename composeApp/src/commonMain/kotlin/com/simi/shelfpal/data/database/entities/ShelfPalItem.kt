package com.simi.shelfpal.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettings

@Entity
data class ShelfPalItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productImageUrl: String? = null,
    val productExpiryImageUrl: String? = null,
    @Embedded val basicInfo: ProductBasicInfo,
    @Embedded val expiryInfo: ProductExpiryInfo,
    @Embedded val reminderSettings: ShelfPalItemReminderSettings = ShelfPalItemReminderSettings()
)