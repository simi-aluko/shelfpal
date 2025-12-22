package com.simi.shelfpal.ui.additem.repository

import com.simi.shelfpal.data.database.entities.ShelfPalItem

interface AddItemRepository {
    suspend fun saveItem(shelfPalItem: ShelfPalItem)
}