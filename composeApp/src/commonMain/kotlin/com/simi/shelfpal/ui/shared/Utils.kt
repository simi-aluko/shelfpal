package com.simi.shelfpal.ui.shared

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.theme.errorContainerLight
import com.simi.shelfpal.ui.theme.errorDark
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.inversePrimaryLight
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import com.simi.shelfpal.ui.theme.warningContainerLight
import com.simi.shelfpal.ui.theme.warningDark
import com.simi.shelfpal.ui.theme.warningLight
import com.simi.shelfpal.utils.getExpiryDate
import com.simi.shelfpal.utils.getLocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun String.capitalizeFirstChar() = this.toLowerCase(Locale.current).replaceFirstChar { it.uppercase() }

fun getDisplayMonths() = Month.entries
    .map { month ->
        month.name.capitalizeFirstChar()
    }

enum class ItemColorStatus { EXPIRED, USED, PENDING }

fun getItemColorStatus(expired: Boolean, used: Boolean): ItemColorStatus = when {
    expired -> ItemColorStatus.EXPIRED
    used -> ItemColorStatus.USED
    else -> ItemColorStatus.PENDING
}

fun getStatusBorderColor(status: ItemColorStatus) = when (status) {
    ItemColorStatus.EXPIRED -> errorDark
    ItemColorStatus.USED -> warningDark
    ItemColorStatus.PENDING -> inversePrimaryLight
}

fun getStatusContainerColor(status: ItemColorStatus) = when (status) {
    ItemColorStatus.EXPIRED -> errorContainerLight
    ItemColorStatus.USED -> warningContainerLight
    ItemColorStatus.PENDING -> primaryContainerLight
}

fun getStatusLabelColor(status: ItemColorStatus) = when (status) {
    ItemColorStatus.EXPIRED -> errorLight
    ItemColorStatus.USED -> warningLight
    ItemColorStatus.PENDING -> tertiaryLight
}

@OptIn(ExperimentalTime::class)
fun getExpiredStatus(expiryInfo: ProductExpiryInfo): Boolean {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    val currentYear = localDateTime.year
    val expiryMonth = expiryInfo.expiryMonth
    val expiryDay = expiryInfo.expiryDay

    return expiryInfo.expiryYear?.let { expiryYear ->
        when {
            currentYear > expiryYear -> true
            currentYear == expiryYear -> {
                if (expiryMonth != null) {
                    if (localDateTime.month.number > expiryMonth) {
                        true
                    } else if (localDateTime.month.number < expiryMonth) {
                        false
                    } else {
                        // TODO: If day is today, it has not expired. but should use yellow color to signal it is soon
                        // day == expiry, not yet expired. but less than 2 weeks. yellow color
                        // day > expiry then expired. red color
                        if(expiryDay != null && localDateTime.day > expiryDay) {
                            true
                        } else {
                            false
                        }
                    }
                } else {
                    false
                }
            }
            else -> false
        }
    } ?: false
}

fun List<ShelfPalItem>.filterExpired() = this.filter {
    it.expiryInfo.getExpiryDate()?.let {
        getLocalDateTime().date.daysUntil(it) < 0
    } ?: false
}