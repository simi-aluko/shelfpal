package com.simi.shelfpal

import android.content.Context
import com.simi.shelfpal.data.database.AppDatabase
import com.simi.shelfpal.data.database.getDatabaseBuilder
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.database.getRoomDatabase
import com.simi.shelfpal.data.firebase.FirebaseConnectorImpl
import com.simi.shelfpal.koin.commonModules
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication.Companion.init
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class KoinModules(context: Context) {
    init {
        startKoin {
            androidContext(context)
            modules(
                module {
                    single<FirebaseConnector> {
                        FirebaseConnectorImpl()
                    }
                    single<AppDatabase> {
                        getRoomDatabase(
                            builder = getDatabaseBuilder(get())
                        )
                    }
                },
                commonModules
            )
        }
    }
}