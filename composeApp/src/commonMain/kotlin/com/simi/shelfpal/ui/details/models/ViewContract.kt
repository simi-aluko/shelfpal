package com.simi.shelfpal.ui.details.models

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettings
import org.jetbrains.compose.resources.DrawableResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.arrowback

sealed class ViewStates {
    data class ItemDetail(
        val itemDetailHeader: ItemDetailHeader = ItemDetailHeader(),
        val itemDetailImages: ItemDetailImages = ItemDetailImages(),
        val itemDetailExpiryInfo: ItemDetailExpiryInfo = ItemDetailExpiryInfo(),
        val reminderSettings: ShelfPalItemReminderSettings = ShelfPalItemReminderSettings()
    ) : ViewStates()
}

data class ItemDetailHeader(
    val title: String = "",
    val subtitle: String = "",
    val actionIcon: DrawableResource? = null,
    val navigationIcon: DrawableResource = Res.drawable.arrowback,
)

data class ItemDetailImages(
    val productImageUrl: String = "",
    val expiryImageUrl: String = "",
)

data class ItemDetailExpiryInfo(
    val expiryDate: String = "",
    val timeRemaining: String? = null,
    val hasExpired: Boolean = false
)

data class ViewMetaData(
    var shelfPalItem: ShelfPalItem? = null
)