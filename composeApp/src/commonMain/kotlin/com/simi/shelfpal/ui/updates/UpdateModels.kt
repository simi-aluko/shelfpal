package com.simi.shelfpal.ui.updates

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

data class ShelfPalUpdate(
    val title: String,
    val subtitle: String,
    val isNew: Boolean?,
    val updateRecency: UpdateRecency? = null,
    val items: List<ShelfPalUpdateItem>?,
    val headerIcon: UpdateHeaderIcon
)

data class ShelfPalUpdateItem(
    val id: Long,
    val title: String,
    val category: String
)

data class UpdateHeaderIcon(
    val icon: DrawableResource,
    val tint: Color,
    val backgroundColor: Color
)

@Serializable
data class AiExpirySummary(
    val title: String,
    val body: String
)

data class LoadingState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

enum class UpdateRecency(val key: String) {
    TODAY("today"),
    WEEK("week"),
    MONTH("month"),
    CUSTOM("custom")
}