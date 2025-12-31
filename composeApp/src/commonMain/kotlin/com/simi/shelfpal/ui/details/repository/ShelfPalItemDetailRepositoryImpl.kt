package com.simi.shelfpal.ui.details.repository

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ShelfPalItem

class ShelfPalItemDetailRepositoryImpl(
    private val shelfPalItemsDao: ShelfPalItemsDao
) : ShelfPalItemDetailRepository {

    override fun getItem(id: Long) = shelfPalItemsDao.getById(id)

    override suspend fun updateItem(item: ShelfPalItem) = shelfPalItemsDao.update(item)
    override suspend fun deleteItem(item: ShelfPalItem) = shelfPalItemsDao.deleteById(item)

}