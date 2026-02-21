package com.simi.shelfpal.utils

import com.simi.shelfpal.data.models.ProductExpiryInfo
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getLocalDateTime(): LocalDateTime {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.Companion.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    return localDateTime
}

fun ProductExpiryInfo.getExpiryDate(): LocalDate? {
    return expiryYear?.let {
        if (hasDay && expiryDay != null && expiryMonth != null) {
            LocalDate(expiryYear, expiryMonth, expiryDay)
        } else if(!hasDay && expiryDay == null && expiryMonth != null) {
            LocalDate(expiryYear, expiryMonth, 1).plus(1, DateTimeUnit.MONTH)
        } else {
            null
        }
    }
}