package com.simi.shelfpal.notification

data class ExpiryNotification(
    val itemId: Long,
    val title: String,
    val body: String,
)

