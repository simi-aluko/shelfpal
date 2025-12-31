package com.simi.shelfpal.ui.details.models

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.home.models.getExpiredStatus
import com.simi.shelfpal.ui.shared.getDisplayMonths
import com.simi.shelfpal.ui.shared.getLocalDateTime
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus

fun ShelfPalItem.toViewModel() = ViewStates.ItemDetail(
    itemDetailHeader = ItemDetailHeader(
        title = basicInfo.name,
        subtitle = basicInfo.category
    ),
    itemDetailExpiryInfo = ItemDetailExpiryInfo(
        expiryDate = expiryInfo.toDisplayDate(),
        timeRemaining = expiryInfo.toDetailedTimeRemaining(),
        hasExpired = getExpiredStatus(expiryInfo)
    ),
    itemDetailImages = ItemDetailImages(
        productImageUrl = "https://picsum.photos/id/237/200/300",
        expiryImageUrl = "https://picsum.photos/id/230/200/300"
    ),
    reminderSettings = reminderSettings.copy(hasExpired = getExpiredStatus(expiryInfo))
)

private fun ProductExpiryInfo.toDisplayDate(): String {
    var day = ""
    var month = ""
    var year = ""

    if (hasDay && expiryDay != null && expiryDay in 1..31) {
        day = "$expiryDay "
    }

    if (expiryMonth != null && expiryMonth in 1..12) {
        month = getDisplayMonths()[expiryMonth - 1] + ", "
    }

    if (expiryYear != null) {
        year = expiryYear.toString()
    }

    return "$day$month$year"
}

private fun ProductExpiryInfo.toDetailedTimeRemaining(): String? {
    // with nullable day, month and year, return exact time remaining
    // e.g. 2 years, 3 months and 2 days
    // date from today or date in the past when it expired.

    val hasExpired = getExpiredStatus(this)
    val localDateTime = getLocalDateTime()
    val currentDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.day)

    val expiryDate = expiryYear?.let {
        if (hasDay && expiryDay != null && expiryMonth != null) {
            LocalDate(expiryYear, expiryMonth, expiryDay)
        } else if(!hasDay && expiryDay == null && expiryMonth != null) {
            LocalDate(expiryYear, expiryMonth, 1).plus(1, DateTimeUnit.MONTH)
        } else {
            null
        }
    }

    return expiryDate?.let{
        val daysRemaining = if (hasExpired) {
            expiryDate.daysUntil(currentDate)
        } else {
            currentDate.daysUntil(expiryDate)
        }

        val year = daysRemaining/365
        val month = (daysRemaining%365)/30
        val days = (daysRemaining%365)%30

        val displayYear = if (year != 0) {
            " $year year(s), "
        } else {
            ""
        }
        val displayMonth = if(month != 0) {
            " $month month(s), "
        } else {
            ""
        }
        val displayDays = " $days day(s)"

        if (hasExpired) {
            "Expired$displayYear$displayMonth$displayDays ago"
        } else {
            "Expires in$displayYear$displayMonth$displayDays"
        }
    }
}