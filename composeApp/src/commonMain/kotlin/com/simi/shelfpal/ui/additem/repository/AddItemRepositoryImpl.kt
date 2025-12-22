package com.simi.shelfpal.ui.additem.repository

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ShelfPalItem

class AddItemRepositoryImpl(
    private val shelfPalItemsDao: ShelfPalItemsDao
) : AddItemRepository {

    override suspend fun saveItem(shelfPalItem: ShelfPalItem) {
        shelfPalItemsDao.insert(shelfPalItem)
    }
}