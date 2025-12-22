package com.simi.shelfpal.koin

import com.simi.shelfpal.data.database.AppDatabase
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.ui.home.datasource.HomeLocalDatasource
import com.simi.shelfpal.ui.home.repository.HomeRepository
import com.simi.shelfpal.ui.home.repository.HomeRepositoryImpl
import org.koin.dsl.module

val commonModules = module {
    factory<HomeRepository> {
        HomeRepositoryImpl(
            homeLocalDatasource = get()
        )
    }
    factory<HomeLocalDatasource> {
        HomeLocalDatasource(
            dao = get()
        )
    }
    factory<ShelfPalItemsDao> {
        get<AppDatabase>().getShelfPalItemsDao()
    }
}
