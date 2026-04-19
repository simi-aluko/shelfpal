package com.simi.shelfpal.ui.updates.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

interface UpdatesRepository {
    suspend fun getUpdates(year: Int): Flow<List<ShelfPalItem>>

    suspend fun getUpdates(items: List<Long>): Flow<List<ShelfPalItem>>

    suspend fun markAsUsed(itemId: Long)

    suspend fun markAllAsUsed(itemIds: List<Long>)


}