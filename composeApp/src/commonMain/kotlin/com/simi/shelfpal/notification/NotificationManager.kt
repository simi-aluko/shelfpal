package com.simi.shelfpal.notification

import com.mmk.kmpnotifier.notification.NotifierManager
import com.simi.shelfpal.data.database.daos.ExpiryUpdatesDao
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.data.database.entities.ExpiryUpdate
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class NotificationManager(
    private val shelfPalItemsDao: ShelfPalItemsDao,
    private val expiryUpdatesDao: ExpiryUpdatesDao
) {

    suspend fun getExpiryNotifications(title: String) = shelfPalItemsDao
        .getItems()
        .mapNotNull { it.toExpiryNotification(title) }
        .toSet()

    suspend fun sendNotification(notification: ExpiryNotification) {
        val notifier = NotifierManager.getLocalNotifier()
        notifier.notify {
            id = Random.nextInt(0, Int.MAX_VALUE)
            title = notification.title
            body = notification.body
        }
        postUpdate(notification)
    }

    @OptIn(ExperimentalTime::class)
    suspend fun postUpdate(expiryNotification: ExpiryNotification) {
        val update = expiryUpdatesDao.getByItemId(expiryNotification.itemId)
        if (update != null) {
            val newUpdate = createUpdateCopy(update, expiryNotification.expiryStatus)
            expiryUpdatesDao.update(
                update = newUpdate.copy(
                    expiryStatus = expiryNotification.expiryStatus,
                    updatedAt = Clock.System.now().toEpochMilliseconds()
                )
            )
        } else {
            expiryUpdatesDao.insert(
                ExpiryUpdate(
                    itemId = expiryNotification.itemId,
                    expiryStatus = expiryNotification.expiryStatus,
                    updatedAt = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    private fun createUpdateCopy(update: ExpiryUpdate, expiryStatus: ExpiryStatus) = when (expiryStatus) {
        ExpiryStatus.EXPIRED -> update.copy(expiredReviewed = false)
        ExpiryStatus.EXPIRING_TODAY -> update.copy(todayReviewed = false)
        ExpiryStatus.EXPIRING_IN_A_WEEK -> update.copy(weekReviewed = false)
        ExpiryStatus.EXPIRING_IN_A_MONTH -> update.copy(monthReviewed = false)
        ExpiryStatus.EXPIRING_IN_CUSTOM_DURATION -> update.copy(customDurationReviewed = false)
    }
}