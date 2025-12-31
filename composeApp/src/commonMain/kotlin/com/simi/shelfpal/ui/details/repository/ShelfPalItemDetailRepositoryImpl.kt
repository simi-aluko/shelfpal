package com.simi.shelfpal.ui.details.repository

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao

class ShelfPalItemDetailRepositoryImpl(
    private val shelfPalItemsDao: ShelfPalItemsDao
) : ShelfPalItemDetailRepository {

    override fun getItem(id: Long) = shelfPalItemsDao.getById(id)

}