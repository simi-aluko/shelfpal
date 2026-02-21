package com.simi.shelfpal

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

class ShelfPalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinModules(this@ShelfPalApplication)

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground
            )
        )
    }
}
