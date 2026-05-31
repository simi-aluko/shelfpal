package com.simi.shelfpal

import com.simi.shelfpal.data.database.AppDatabase
import com.simi.shelfpal.data.firebase.FirebaseConnector
import com.simi.shelfpal.data.database.getRoomDatabase
import com.simi.shelfpal.data.database.getDatabaseBuilder
import com.simi.shelfpal.koin.commonModules
import com.simi.shelfpal.notification.BackgroundScheduler
import com.simi.shelfpal.notification.IosBackgroundScheduler
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinModules {
    fun initKoin(
        firebaseConnector: FirebaseConnector
    ) {
        startKoin {
            modules(
                module {
                    single<FirebaseConnector> {
                        firebaseConnector
                    }
                    single<AppDatabase> {
                        getRoomDatabase(
                            builder = getDatabaseBuilder()
                        )
                    }
                    factory<BackgroundScheduler> {
                        IosBackgroundScheduler()
                    }
                },
                commonModules
            )
        }
    }
}