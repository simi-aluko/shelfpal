package com.simi.shelfpal.expirymanager

import com.mmk.kmpnotifier.notification.NotifierManager
import com.simi.shelfpal.data.database.daos.ShelfPalItemsDao
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.utils.getExpiryDate
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.until
import kotlin.random.Random

class ExpiryManager(private val shelfPalItemsDao: ShelfPalItemsDao) {

    suspend fun getItemsToNotifyAbout(
        title: String
    ) {
        shelfPalItemsDao
            .getItems()
            .map { it.toExpiryNotification() }
            .forEach {
                if (it != null) {
                    sendNotification(title, it)
                }
            }
        }
    }

    private fun sendNotification(
        notificationTitle: String,
        notificationBody: String
    ) {
        val notifier = NotifierManager.getLocalNotifier()
        notifier.notify {
            id= Random.nextInt(0, Int.MAX_VALUE)
            title = notificationTitle
            body = notificationBody
        }
    }

private fun ShelfPalItem.toExpiryNotification(): String? {
    val currentDate = getLocalDateTime().date
    val expiryDate = expiryInfo.getExpiryDate()

    return if (!reminderSettings.hasExpired && expiryDate != null) {
        val notifyMonthBefore = shouldNotify(
            notify = reminderSettings.notifyMonthBefore,
            currentDate = currentDate,
            expiryDate = expiryDate,
            unit = DateTimeUnit.MONTH,
        )

        val notifyWeekBefore = shouldNotify(
            notify = reminderSettings.notifyWeekBefore,
            currentDate = currentDate,
            expiryDate = expiryDate,
            expectedDifference = 7L
        )

        val notifyDayBefore = shouldNotify(
            notify = reminderSettings.notifyDayBefore,
            currentDate = currentDate,
            expiryDate = expiryDate,
        )

        val notifyOnDay = reminderSettings.notifyOnDay && currentDate == expiryDate

        val notifyCustomDaysBefore = shouldNotify(
            notify = reminderSettings.customDaysBefore != null,
            currentDate = currentDate,
            expiryDate = expiryDate,
            expectedDifference = reminderSettings.customDaysBefore?.toLong() ?: 0
        )


        when {
            notifyMonthBefore -> {
                "Friendly Nudge: Your ${basicInfo.name} would be expiring in a month's time."
            }
            notifyWeekBefore -> {
                "Planning your week? Your ${basicInfo.name} expires in 7 days."
            }
            notifyDayBefore -> {
                "Heads up! Your ${basicInfo.name} expires tomorrow. Don't let it go to waste."
            }
            notifyOnDay -> {
                "Last chance! Your ${basicInfo.name} is expires today."
            }
            notifyCustomDaysBefore -> {
                "Friendly reminder: Your ${basicInfo.name} expires in ${reminderSettings.customDaysBefore} day(s)"
            }
            else -> null
        }
    } else {
        null
    }
}

private fun shouldNotify(
    notify: Boolean,
    currentDate: LocalDate,
    expiryDate: LocalDate,
    unit: DateTimeUnit.DateBased = DateTimeUnit.DAY,
    expectedDifference: Long = 1L
) = notify && currentDate.until(expiryDate, unit) == expectedDifference

