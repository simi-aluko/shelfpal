package com.simi.shelfpal.ui.additem.utils

import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemBasicInfo
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemExpiryInfo
import com.simi.shelfpal.ui.additem.models.BasicInfoError
import com.simi.shelfpal.ui.additem.models.ExpiryInfoError
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun validateBasicInfo(
    addEditItemBasicInfo: AddEditItemBasicInfo,
    categories: List<String>
): BasicInfoError {
    val categoryValid = addEditItemBasicInfo.category.isNotBlank() && addEditItemBasicInfo.category in categories
    val nameValid = addEditItemBasicInfo.productName.isNotBlank()

    return BasicInfoError(
        nameError = !nameValid,
        categoryError = !categoryValid
    )
}

fun validateExpiryInfo(addEditItemExpiryInfo: AddEditItemExpiryInfo): ExpiryInfoError {
    val dayValid = addEditItemExpiryInfo.day.isNotBlank() && addEditItemExpiryInfo.day.toInt() in 1..31
    val monthValid = addEditItemExpiryInfo.month.isNotBlank() && addEditItemExpiryInfo.month in getDisplayMonths()
    val yearValid = addEditItemExpiryInfo.year.isNotBlank() && addEditItemExpiryInfo.year in getSelectableYearList()

    return ExpiryInfoError(
        dayError = addEditItemExpiryInfo.hasDay && !dayValid,
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
