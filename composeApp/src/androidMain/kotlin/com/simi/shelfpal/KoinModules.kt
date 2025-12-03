package com.simi.shelfpal

import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinModules {
    init {
        startKoin {
            modules(
                module { single<FirebaseConnector> { FirebaseConnectorImpl() } }
            )
        }
    }
}