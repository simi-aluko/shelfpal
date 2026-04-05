package com.simi.shelfpal.notification

import com.simi.shelfpal.data.database.entities.ExpiryStatus

data class ExpiryNotification(
    val itemId: Long,
    val title: String,
    val body: String,
    val expiryStatus: ExpiryStatus,
)