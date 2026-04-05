package com.simi.shelfpal.notification

import com.simi.shelfpal.data.database.entities.ExpiryStatus
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.utils.getExpiryDate
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.until

fun ShelfPalItem.toExpiryNotification(title: String): ExpiryNotification? {
    val currentDate = getLocalDateTime().date
    val expiryDate = expiryInfo.getExpiryDate()

    return if (!reminderSettings.hasExpired && expiryDate != null) {
        val notifyMonthBefore = shouldNotify(
            notify = reminderSettings.notifyMonthBefore,
            currentDate = currentDate,
            expiryDate = expiryDate,
            unit = DateTimeUnit.Companion.MONTH,
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

        val (body, status) = when {
            notifyMonthBefore -> {
                Pair("Friendly Nudge: Your ${basicInfo.name} would be expiring in a month's time.", ExpiryStatus.EXPIRING_IN_A_MONTH)
            }
            notifyWeekBefore -> {
                    Pair("Planning your week? Your ${basicInfo.name} expires in 7 days.",ExpiryStatus.EXPIRING_IN_A_WEEK)
            }
            notifyDayBefore -> {
                Pair("Heads up! Your ${basicInfo.name} expires tomorrow. Don't let it go to waste.", ExpiryStatus.EXPIRING_IN_CUSTOM_DURATION)
            }
            notifyOnDay -> {
                Pair("Last chance! Your ${basicInfo.name} is expires today.", ExpiryStatus.EXPIRING_TODAY)
            }
            notifyCustomDaysBefore -> {
                Pair("Heads up! Your ${basicInfo.name} expires in ${reminderSettings.customDaysBefore} days.", ExpiryStatus.EXPIRING_IN_CUSTOM_DURATION)
            }
            else -> Pair(null, null)
        }
        if (body != null && status != null) {
            ExpiryNotification(
                title = title,
                body = body,
                itemId = id,
                expiryStatus = status
            )
        } else null
    } else {
        null
    }
}

private fun shouldNotify(
    notify: Boolean,
    currentDate: LocalDate,
    expiryDate: LocalDate,
    unit: DateTimeUnit.DateBased = DateTimeUnit.Companion.DAY,
    expectedDifference: Long = 1L
) = notify && currentDate.until(expiryDate, unit) == expectedDifference