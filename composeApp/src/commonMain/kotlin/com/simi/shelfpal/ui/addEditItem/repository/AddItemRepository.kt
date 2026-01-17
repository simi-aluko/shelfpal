package com.simi.shelfpal.ui.addEditItem.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import dev.gitlive.firebase.storage.Data
import kotlinx.coroutines.flow.Flow

interface AddItemRepository {
    suspend fun saveItem(shelfPalItem: ShelfPalItem)
    fun getItem(id: Long): Flow<ShelfPalItem?>
    suspend fun updateItem(shelfPalItem: ShelfPalItem)
    suspend fun uploadImage(data: Data, fileName: String): String
}