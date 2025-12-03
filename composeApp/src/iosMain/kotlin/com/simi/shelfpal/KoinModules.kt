package com.simi.shelfpal

import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinModules {
    fun initKoin(
        firebaseConnector: FirebaseConnector
    ) {
        startKoin {
            modules(
                module { single<FirebaseConnector> { firebaseConnector } }
            )
        }
    }
}