package com.simi.shelfpal.ui.shared

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.simi.shelfpal.ui.theme.errorContainerLight
import com.simi.shelfpal.ui.theme.errorDark
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.inversePrimaryLight
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import kotlinx.datetime.Month

fun String.capitalizeFirstChar() = this.toLowerCase(Locale.current).replaceFirstChar { it.uppercase() }

fun getDisplayMonths() = Month.entries
    .map { month ->
        month.name.capitalizeFirstChar()
    }

fun getExpiredBorderColor(expired: Boolean) = if (expired) {
    errorDark
} else {
    inversePrimaryLight
}

fun getExpiredContainerColor(expired: Boolean) = if (expired) {
    errorContainerLight
} else {
    primaryContainerLight
}

fun getExpiredLabelColor(expired: Boolean) = if (expired) {
    errorLight
} else {
    tertiaryLight
}