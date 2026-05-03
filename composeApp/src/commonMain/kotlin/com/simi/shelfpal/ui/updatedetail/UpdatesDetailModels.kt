package com.simi.shelfpal.ui.updatedetail

import com.simi.shelfpal.ui.updates.UpdateRecency
import kotlinx.serialization.Serializable


data class UpdatesDetailItem(
    val id: Long,
    val name: String,
    val category: String,
)

data class UpdatesDetailState(
    val items: List<UpdatesDetailItem> = emptyList(),
    val title: String = "",
    val subtitle: String = "",
)

@Serializable
data class UpdatesDetailParams(
    val items: List<Long>,
    val title: String,
    val subtitle: String,
    val showActions: Boolean = true,
    val updateRecency: UpdateRecency? = null,
    val entrySource: UpdateDetailsEntrySource = UpdateDetailsEntrySource.RECENT_ACTIVITY
)

enum class UpdateDetailsEntrySource {
    RECENT_ACTIVITY,
    EXPIRED_MENU,
    USED_MENU
}