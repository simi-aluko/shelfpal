package com.simi.shelfpal.ui.additem.utils

import com.simi.shelfpal.ui.additem.models.AddItemViewState.BasicInfo
import com.simi.shelfpal.ui.additem.models.AddItemViewState.ExpiryInfo
import com.simi.shelfpal.ui.additem.models.BasicInfoError
import com.simi.shelfpal.ui.additem.models.ExpiryInfoError
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun validateBasicInfo(
    basicInfo: BasicInfo,
    categories: List<String>
): BasicInfoError {
    val categoryValid = basicInfo.category.isNotBlank() && basicInfo.category in categories
    val nameValid = basicInfo.productName.isNotBlank()

    return BasicInfoError(
        nameError = !nameValid,
        categoryError = !categoryValid
    )
}

fun validateExpiryInfo(expiryInfo: ExpiryInfo): ExpiryInfoError {
    val dayValid = expiryInfo.day.isNotBlank() && expiryInfo.day.toInt() in 1..31
    val monthValid = expiryInfo.month.isNotBlank() && expiryInfo.month in Month.entries.map { it.name.capitalizeFirstChar() }
    val yearValid = expiryInfo.year.isNotBlank() && expiryInfo.year in getSelectableYearList()

    return ExpiryInfoError(
        dayError = expiryInfo.hasDay && !dayValid,
        monthError = !monthValid,
        yearError = !yearValid
    )
}

@OptIn(ExperimentalTime::class)
fun getSelectableYearList(): List<String> {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val currentYear = now.toLocalDateTime(localTimeZone).year
    val maxYearCount = 10

    return (currentYear..(currentYear+maxYearCount)).map { it.toString() }
}