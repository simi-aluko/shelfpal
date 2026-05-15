package com.simi.shelfpal

import android.content.Context
import com.simi.shelfpal.data.database.AppDatabase
import com.simi.shelfpal.data.database.getDatabaseBuilder
import com.simi.shelfpal.data.database.getRoomDatabase
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.firebase.FirebaseConnectorImpl
import com.simi.shelfpal.koin.commonModules
import com.simi.shelfpal.notification.AndroidBackgroundScheduler
import com.simi.shelfpal.notification.BackgroundScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
                    factory<BackgroundScheduler> {
                        AndroidBackgroundScheduler(get())
                    }
                },
                commonModules
            )
        }
    }
}