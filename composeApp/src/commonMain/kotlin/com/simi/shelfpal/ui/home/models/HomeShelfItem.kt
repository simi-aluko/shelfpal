package com.simi.shelfpal.ui.home.models

import org.jetbrains.compose.resources.DrawableResource

data class HomeShelfItem(
    val id: Long,
    val productName: String,
    val productImage: DrawableResource,
    val expiryText: String,
    val expired: Boolean,
    val durationRemaining: String
)