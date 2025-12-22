package com.simi.shelfpal.ui.home.datasource

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao

class HomeLocalDatasource(private val dao: ShelfPalItemsDao) {

    fun getAllItems() = dao.getAll()
}