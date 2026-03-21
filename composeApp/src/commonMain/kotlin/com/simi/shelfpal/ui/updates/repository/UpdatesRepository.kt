package com.simi.shelfpal.ui.updates.repository

import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.data.database.entities.UpdateWithItem

interface UpdatesRepository {
    suspend fun getUpdates(
        status: ExpiryStatus,
        isReviewed: Boolean = false
    ): List<UpdateWithItem>
}