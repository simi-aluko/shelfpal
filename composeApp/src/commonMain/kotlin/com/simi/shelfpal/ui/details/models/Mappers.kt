package com.simi.shelfpal.ui.details.models

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.shared.formatEpochMillis
import com.simi.shelfpal.ui.shared.getDisplayMonths
import com.simi.shelfpal.ui.shared.getExpiredStatus
import com.simi.shelfpal.utils.getExpiryDate
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.edit

fun ShelfPalItem.toViewModel() = ViewStates.ItemDetail(
    itemDetailHeader = ItemDetailHeader(
        title = basicInfo.name,
        subtitle = basicInfo.category,
        actionIcon = if (getExpiredStatus(expiryInfo) || used) {
            null
        } else {
            Res.drawable.edit
        }
    ),
    itemDetailExpiryInfo = ItemDetailExpiryInfo(
        expiryDate = expiryInfo.toDisplayDate(),
        timeRemaining = expiryInfo.toDetailedTimeRemaining().takeIf { !used },
        hasExpired = getExpiredStatus(expiryInfo),
        addedDate = if (createdDate > 0) formatEpochMillis(createdDate) else "",
        usedDate = usedDate?.let { formatEpochMillis(it) },
        isUsed = used
    ),
    itemDetailImages = ItemDetailImages(
        productImageUrl = productImageUrl.orEmpty(),
        expiryImageUrl = productExpiryImageUrl.orEmpty()
    ),
    reminderSettings = reminderSettings.copy(hasExpired = getExpiredStatus(expiryInfo) || used)
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

fun ProductExpiryInfo.toDetailedTimeRemaining(): String? {
    // with nullable day, month and year, return exact time remaining
    // e.g. 2 years, 3 months and 2 days
    // date from today or date in the past when it expired.

    val hasExpired = getExpiredStatus(this)
    val localDateTime = getLocalDateTime()
    val currentDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.day)
    val expiryDate = getExpiryDate()

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