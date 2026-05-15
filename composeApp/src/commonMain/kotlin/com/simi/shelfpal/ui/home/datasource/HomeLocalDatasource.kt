package com.simi.shelfpal.ui.home.datasource

import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao

class HomeLocalDatasource(private val dao: ShelfPalItemsDao) {

    fun getAllItems(
        category: String?,
        month: Int?,
        year: Int?,
        used: Boolean
    ) = dao.getItems(
        category = category,
        month = month,
        year = year,
        used = used
    )
}