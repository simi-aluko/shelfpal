package com.simi.shelfpal.ui.updates.repository

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import kotlinx.coroutines.flow.Flow

class UpdatesRepositoryImpl(
    private val shelfPalItemsDao: ShelfPalItemsDao,
) : UpdatesRepository {

    override suspend fun getUpdates(year: Int): Flow<List<ShelfPalItem>> = shelfPalItemsDao.getItems(
        category = null,
        month = null,
        year = year
    )

    override suspend fun getUpdates(items: List<Long>) = shelfPalItemsDao.getItems(items)

    override suspend fun markAsUsed(itemId: Long) = shelfPalItemsDao.markAsUsed(itemId)

    override suspend fun markAllAsUsed(itemIds: List<Long>) = shelfPalItemsDao.markAsUsed(itemIds)

}