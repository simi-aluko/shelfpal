package com.simi.shelfpal.notification

import com.mmk.kmpnotifier.notification.NotifierManager
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.ui.shared.getExpiredStatus
import kotlin.random.Random

class NotificationManager(
    private val shelfPalItemsDao: ShelfPalItemsDao
) {

    suspend fun getExpiryNotifications(title: String) = shelfPalItemsDao
        .getItems()
        .mapNotNull { it.toExpiryNotification(title) }
        .toSet()

    suspend fun disableExpiredItemReminders() {
        shelfPalItemsDao.getItems()
            .filter { getExpiredStatus(it.expiryInfo) }
            .forEach {
                shelfPalItemsDao.disableReminders(it.id)
            }
    }

    fun sendNotification(notification: ExpiryNotification) {
        val notifier = NotifierManager.getLocalNotifier()
        notifier.notify {
            id = Random.nextInt(0, Int.MAX_VALUE)
            title = notification.title
            body = notification.body
        }
    }
}

