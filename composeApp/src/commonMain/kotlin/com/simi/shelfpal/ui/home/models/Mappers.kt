package com.simi.shelfpal.ui.home.models

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getExpiredStatus
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun ShelfPalItem.toHomeShelfItem(): HomeShelfItem = HomeShelfItem(
    id = id,
    productName = basicInfo.name,
    category = basicInfo.category,
    expiryText = getExpiryText(expiryInfo),
    expired = getExpiredStatus(expiryInfo),
    used = used,
    durationRemaining = getDurationRemaining(expiryInfo, used),
    expiryYear = expiryInfo.expiryYear
)


@OptIn(ExperimentalTime::class)
private fun getExpiryText(expiryInfo: ProductExpiryInfo): String {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    val currentYear = localDateTime.year
    val expiryMonth = expiryInfo.expiryMonth
    val expiryDay = expiryInfo.expiryDay

    return expiryInfo.expiryYear?.let { expiryYear ->
        when {
            currentYear > expiryYear -> "Expired"

            currentYear < expiryYear -> if (expiryMonth != null && expiryMonth in 1..12) {
                "Expires in ${Month(expiryInfo.expiryMonth).name.capitalizeFirstChar()} $expiryYear"
            } else {
                "Expires in $expiryYear"
            }

            currentYear == expiryYear -> if (expiryDay != null && expiryMonth != null && expiryDay in 1..31 && expiryMonth in 1..12) {
                "Expires on ${Month(expiryInfo.expiryMonth).name.capitalizeFirstChar()} $expiryDay"
            } else if (expiryDay == null && expiryMonth != null && expiryMonth in 1..12) {
                "Expires in ${Month(expiryInfo.expiryMonth).name.capitalizeFirstChar()}"
            } else {
                "Expires this year"
            }

            else -> ""
        }
    } ?: ""
}

private fun getDurationRemaining(expiryInfo: ProductExpiryInfo, used: Boolean): String {
    if (used) return "Used"

    if (getExpiredStatus(expiryInfo)) {
        return getFormattedDaysRemaining(-1)
    }

    val expiryYear = expiryInfo.expiryYear
    val expiryMonth = expiryInfo.expiryMonth
    val expiryDay = expiryInfo.expiryDay

    val localDateTime = getLocalDateTime()
    val currentDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.day)

    return if (expiryYear != null) {
        if (expiryMonth != null && expiryDay != null) {
            val expiryDate = LocalDate(expiryYear, expiryMonth, expiryDay)
            val daysRemaining = currentDate.daysUntil(expiryDate)
            return getFormattedDaysRemaining(daysRemaining)
        } else if (expiryMonth != null && expiryDay == null) {
            val expiryDate = LocalDate(expiryYear, expiryMonth, 1).plus(1, DateTimeUnit.MONTH)
            val daysRemaining = currentDate.daysUntil(expiryDate)
            return getFormattedDaysRemaining(daysRemaining)
        } else {
            ""
        }
    } else {
        return ""
    }
}

private fun getFormattedDaysRemaining(daysRemaining: Int): String {
    if (daysRemaining < 0) {
        return "Expired"
    }

    return if (daysRemaining >= 365 * 2) {
        "${daysRemaining/365} years"
    } else {
        if (daysRemaining >= 60) {
            "${daysRemaining/30} months"
        } else {
            if (daysRemaining == 1) {
                "1 day"
            } else {
                "$daysRemaining days"
            }
        }
    }
}