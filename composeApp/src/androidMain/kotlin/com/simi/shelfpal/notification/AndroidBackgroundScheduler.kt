package com.simi.shelfpal.notification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.simi.shelfpal.work.ExpiryNotificationWorker

class AndroidBackgroundScheduler(context: Context) : BackgroundScheduler {

    private val workManager = WorkManager.getInstance(context)

    companion object {
        private const val EXPIRY_NOTIFICATION_WORK = "expiry_notification"
    }

    override fun startScheduler() {
        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = EXPIRY_NOTIFICATION_WORK,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
            ExpiryNotificationWorker.workRequest
        )
    }

    override fun cancelScheduler() {
        workManager.cancelUniqueWork(EXPIRY_NOTIFICATION_WORK)
    }
}