package com.simi.shelfpal.ui.shared

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun String.capitalizeFirstChar() = this.toLowerCase(Locale.current).replaceFirstChar { it.uppercase() }

fun getDisplayMonths() = Month.entries
    .map { month ->
        month.name.capitalizeFirstChar()
    }

@OptIn(ExperimentalTime::class)
fun getLocalDateTime(): LocalDateTime {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    return localDateTime
}