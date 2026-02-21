package com.simi.shelfpal.koin

import com.simi.shelfpal.data.database.AppDatabase
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.expirymanager.ExpiryManager
import com.simi.shelfpal.ui.addEditItem.repository.AddItemRepository
import com.simi.shelfpal.ui.addEditItem.repository.AddItemRepositoryImpl
import com.simi.shelfpal.ui.details.repository.ShelfPalItemDetailRepository
import com.simi.shelfpal.ui.details.repository.ShelfPalItemDetailRepositoryImpl
import com.simi.shelfpal.ui.home.datasource.HomeLocalDatasource
import com.simi.shelfpal.ui.home.repository.HomeRepository
import com.simi.shelfpal.ui.home.repository.HomeRepositoryImpl
import org.koin.dsl.module

val commonModules = module {
    factory<ShelfPalItemsDao> {
        get<AppDatabase>().getShelfPalItemsDao()
    }
    factory<HomeLocalDatasource> {
        HomeLocalDatasource(
            dao = get()
        )
    }
    factory<HomeRepository> {
        HomeRepositoryImpl(
            homeLocalDatasource = get()
        )
    }
    factory<AddItemRepository> {
        AddItemRepositoryImpl(
            shelfPalItemsDao = get()
        )
    }
    factory<ShelfPalItemDetailRepository> {
        ShelfPalItemDetailRepositoryImpl(
            shelfPalItemsDao = get()
        )
    }
    single {
        ExpiryManager(
            shelfPalItemsDao = get()
        )
    }
}
