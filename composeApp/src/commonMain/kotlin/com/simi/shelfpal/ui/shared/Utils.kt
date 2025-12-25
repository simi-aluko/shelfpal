package com.simi.shelfpal.ui.shared

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase

fun String.capitalizeFirstChar() = this.toLowerCase(Locale.current).replaceFirstChar { it.uppercase() }