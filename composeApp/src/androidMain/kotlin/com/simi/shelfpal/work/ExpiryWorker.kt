package com.simi.shelfpal.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.simi.shelfpal.R
import com.simi.shelfpal.expirymanager.ExpiryManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.util.concurrent.TimeUnit

class ExpiryWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        with(get<ExpiryManager>()) {
            getExpiryNotifications(context.getString(R.string.app_name))
                .forEach { sendNotification(it) }
        }
        return Result.success()
    }
}

val expiryWorkRequest: WorkRequest = PeriodicWorkRequestBuilder<ExpiryWorker>(
    repeatInterval = 1,
    repeatIntervalTimeUnit = TimeUnit.DAYS
).build()