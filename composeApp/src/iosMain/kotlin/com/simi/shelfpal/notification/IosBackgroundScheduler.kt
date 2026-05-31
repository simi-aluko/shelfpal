package com.simi.shelfpal.notification

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.BackgroundTasks.BGAppRefreshTask
import platform.BackgroundTasks.BGAppRefreshTaskRequest
import platform.BackgroundTasks.BGTaskScheduler
import platform.Foundation.NSDate
import platform.Foundation.dateByAddingTimeInterval

class IosBackgroundScheduler : BackgroundScheduler, KoinComponent {

    private val notificationManager by inject<NotificationManager>()

    companion object {
        const val TASK_ID = "com.simi.shelfpal.expiry_check"
    }

    fun registerTask() {
        BGTaskScheduler.sharedScheduler.registerForTaskWithIdentifier(
            identifier = TASK_ID,
            usingQueue = null
        ) { task ->
            handleBackgroundTask(task as BGAppRefreshTask)
        }
    }

    fun handleBackgroundTask(task: BGAppRefreshTask) {
         submitTaskRequest() // Reschedule for next time

        val job = CoroutineScope(Dispatchers.Default).launch {
            notificationManager.disableExpiredItemReminders()
            notificationManager.getExpiryNotifications("ShelfPal")
                .forEach { notificationManager.sendNotification(it) }
            task.setTaskCompletedWithSuccess(true)
        }

        task.expirationHandler = {
            job.cancel()
            task.setTaskCompletedWithSuccess(false)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun submitTaskRequest() {
        val request = BGAppRefreshTaskRequest(TASK_ID)
        request.earliestBeginDate = NSDate().dateByAddingTimeInterval(24.0 * 60.0 * 60.0)
        BGTaskScheduler.sharedScheduler.submitTaskRequest(request, error = null)
    }

    override fun startScheduler() {
         submitTaskRequest()
    }

    override fun cancelScheduler() {
        BGTaskScheduler.sharedScheduler.cancelTaskRequestWithIdentifier(TASK_ID)
    }
}
