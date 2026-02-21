package com.simi.shelfpal.ui.shared

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import kotlinx.datetime.Month

fun String.capitalizeFirstChar() = this.toLowerCase(Locale.current).replaceFirstChar { it.uppercase() }

fun getDisplayMonths() = Month.entries
    .map { month ->
        month.name.capitalizeFirstChar()
    }

