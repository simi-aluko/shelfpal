package com.simi.shelfpal.ui.additem.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

interface AddItemRepository {
    suspend fun saveItem(shelfPalItem: ShelfPalItem)
    fun getItem(id: Long): Flow<ShelfPalItem?>
    suspend fun updateItem(shelfPalItem: ShelfPalItem)
}