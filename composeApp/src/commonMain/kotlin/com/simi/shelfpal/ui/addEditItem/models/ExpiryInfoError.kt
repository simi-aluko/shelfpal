package com.simi.shelfpal.ui.addEditItem.models

import org.jetbrains.compose.resources.StringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.select_day
import shelfpal.composeapp.generated.resources.select_month
import shelfpal.composeapp.generated.resources.select_year

data class ExpiryInfoError(
    val dayError: Boolean = false,
    val monthError: Boolean = false,
    val yearError: Boolean = false,
    val dayErrorText: StringResource = Res.string.select_day,
    val monthErrorText: StringResource = Res.string.select_month,
    val yearErrorText: StringResource = Res.string.select_year
) {
    val hasError: Boolean
        get() = dayError || monthError || yearError
}