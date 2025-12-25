package com.simi.shelfpal.ui.home.models

data class HomeShelfItem(
    val id: Long,
    val productName: String,
    val category: String,
    val expiryText: String,
    val expired: Boolean,
    val durationRemaining: String,
    val expiryYear: Int?
)