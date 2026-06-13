package com.simi.shelfpal.navigation

import com.simi.shelfpal.ui.addEditItem.CameraEntry
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
    data class ImageCapture(val cameraEntry: CameraEntry) : Route()

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
        val showActions: Boolean = true,
        val entrySource: String,
        val updateRecency: String? = null
    ) : Route()

    @Serializable
    data class Webview(
        val title: String,
        val url: String
    ) : Route()
}