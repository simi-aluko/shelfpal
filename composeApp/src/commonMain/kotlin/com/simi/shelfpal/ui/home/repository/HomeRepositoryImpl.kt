package com.simi.shelfpal.ui.home.repository

import com.simi.shelfpal.ui.home.datasource.HomeLocalDatasource

class HomeRepositoryImpl(
    private val homeLocalDatasource: HomeLocalDatasource
) : HomeRepository {

    override fun getItems() = homeLocalDatasource.getAllItems()
}
