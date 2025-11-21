package com.simi.shelfpal

import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinInitHelper {
    fun initKoin(
        logger: ILogger,
        firebaseConnector: FirebaseConnector
    ) {
        startKoin {
            modules(
                module { single<ILogger> { logger } },
                module { single<FirebaseConnector> { firebaseConnector } }
            )
        }
    }
}