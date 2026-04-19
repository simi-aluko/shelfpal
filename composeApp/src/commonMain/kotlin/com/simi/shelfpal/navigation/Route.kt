package com.simi.shelfpal.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    object Welcome : Route()

    @Serializable
    object Home : Route()

    @Serializable
    data class AddEditItem(
        val id: Long? = null
    ) : Route()

    @Serializable
    object ImageCapture : Route()

    @Serializable
    data class ItemDetails(val id: Long, val showActions: Boolean = true) : Route()

    @Serializable
    object Notifications : Route()

    @Serializable
    object Settings : Route()

    @Serializable
    data class UpdatesDetail(
        val items: List<Long>,
        val title: String,
        val subtitle: String,
    ) : Route()
}