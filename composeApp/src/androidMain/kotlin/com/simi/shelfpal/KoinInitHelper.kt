package com.simi.shelfpal

import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinInitHelper {
    init {
        startKoin {
            modules(
                module { single<ILogger> { AndroidLogger() } },
                module { single<FirebaseConnector> { FirebaseConnectorImpl() } }
            )
        }
    }
}