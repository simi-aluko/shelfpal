package com.simi.shelfpal.ui.addEditItem.utils

import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemBasicInfo
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemExpiryInfo
import com.simi.shelfpal.ui.addEditItem.models.BasicInfoError
import com.simi.shelfpal.ui.addEditItem.models.ExpiryInfoError
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.date_in_past
import shelfpal.composeapp.generated.resources.invalid_date
import shelfpal.composeapp.generated.resources.select_day
import shelfpal.composeapp.generated.resources.select_month

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

@OptIn(ExperimentalTime::class)
fun validateExpiryInfo(addEditItemExpiryInfo: AddEditItemExpiryInfo): ExpiryInfoError {
    val dayValid = addEditItemExpiryInfo.day.isNotBlank() && addEditItemExpiryInfo.day.toInt() in 1..31
    val monthValid = addEditItemExpiryInfo.month.isNotBlank() && addEditItemExpiryInfo.month in getDisplayMonths()
    val yearValid = addEditItemExpiryInfo.year.isNotBlank() && addEditItemExpiryInfo.year in getSelectableYearList()

    val selectedMonthNumber = Month.entries.indexOfFirst {
        it.name.capitalizeFirstChar() == addEditItemExpiryInfo.month
    } + 1

    val invalidDateCombo = addEditItemExpiryInfo.hasDay
        && dayValid && monthValid && yearValid && selectedMonthNumber > 0
        && !isValidDateCombination(
            day = addEditItemExpiryInfo.day.toInt(),
            month = selectedMonthNumber,
            year = addEditItemExpiryInfo.year.toInt()
        )

    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val today = LocalDate(now.year, now.monthNumber, now.dayOfMonth)

    val isPastMonth = !invalidDateCombo && monthValid && yearValid
        && !addEditItemExpiryInfo.hasDay && selectedMonthNumber > 0
        && LocalDate(addEditItemExpiryInfo.year.toInt(), selectedMonthNumber, 1) < LocalDate(today.year, today.monthNumber, 1)

    val isPastDay = !invalidDateCombo && dayValid && monthValid && yearValid
        && addEditItemExpiryInfo.hasDay && selectedMonthNumber > 0
        && LocalDate(addEditItemExpiryInfo.year.toInt(), selectedMonthNumber, addEditItemExpiryInfo.day.toInt()) < today

    return ExpiryInfoError(
        dayError = (addEditItemExpiryInfo.hasDay && !dayValid) || invalidDateCombo || isPastDay,
        dayErrorText = when {
            invalidDateCombo -> Res.string.invalid_date
            isPastDay -> Res.string.date_in_past
            else -> Res.string.select_day
        },
        monthError = !monthValid || isPastMonth,
        monthErrorText = if (isPastMonth) Res.string.date_in_past else Res.string.select_month,
        yearError = !yearValid
    )
}

private fun isValidDateCombination(day: Int, month: Int, year: Int): Boolean = try {
    LocalDate(year, month, day)
    true
} catch (e: IllegalArgumentException) {
    false
}

@OptIn(ExperimentalTime::class)
fun getSelectableYearList(): List<String> {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val currentYear = now.toLocalDateTime(localTimeZone).year
    val maxYearCount = 10

    return (currentYear..(currentYear+maxYearCount)).map { it.toString() }
}
