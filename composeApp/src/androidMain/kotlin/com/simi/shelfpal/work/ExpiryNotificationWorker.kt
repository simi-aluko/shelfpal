package com.simi.shelfpal.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.simi.shelfpal.R
import com.simi.shelfpal.notification.NotificationManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.util.concurrent.TimeUnit

class ExpiryNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    companion object {
        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<ExpiryNotificationWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).build()
    }

    override suspend fun doWork(): Result {
        with(get<NotificationManager>()) {
            getExpiryNotifications(context.getString(R.string.app_name))
                .forEach { sendNotification(it) }
        }
        return Result.success()
    }
}
