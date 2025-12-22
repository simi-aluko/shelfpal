package com.simi.shelfpal.ui.home.models

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.shared.models.ProductCategory
import com.simi.shelfpal.ui.shared.models.ProductCategory.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.bread
import shelfpal.composeapp.generated.resources.cleaningtools
import shelfpal.composeapp.generated.resources.drinks
import shelfpal.composeapp.generated.resources.food
import shelfpal.composeapp.generated.resources.medicine
import shelfpal.composeapp.generated.resources.personalcare
import shelfpal.composeapp.generated.resources.petcare
import shelfpal.composeapp.generated.resources.seafood
import shelfpal.composeapp.generated.resources.selectall
import shelfpal.composeapp.generated.resources.spices
import shelfpal.composeapp.generated.resources.vegetable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun ShelfPalItem.toHomeShelfItem(): HomeShelfItem = HomeShelfItem(
    id = id,
    productName = basicInfo.productName,
    productImage = getProductCategoryImage(basicInfo.productCategory),
    expiryText = getExpiryText(expiryInfo),
    expired = getExpiredStatus(expiryInfo),
    durationRemaining = getDurationRemaining(expiryInfo)
)

fun getProductCategoryImage(productCategory: ProductCategory) = when (productCategory) {
    FRESH_FRUITS_AND_VEGETABLES -> Res.drawable.vegetable
    MEATS_DAIRY_AND_FROZEN_FOODS -> Res.drawable.food
    PASTRY_AND_STAPLES -> Res.drawable.bread
    CANNED_FOODS_OILS_SAUCES_AND_SPICES -> Res.drawable.spices
    BEVERAGES -> Res.drawable.drinks
    PREPARED_MEALS -> Res.drawable.seafood
    MEDICATIONS_AND_VITAMINS -> Res.drawable.medicine
    PERSONAL_CARE_AND_BEAUTY -> Res.drawable.personalcare
    CLEANING_AND_HOUSEHOLD -> Res.drawable.cleaningtools
    INFANT_AND_PET_CARE -> Res.drawable.petcare
    OTHER -> Res.drawable.selectall
}

@OptIn(ExperimentalTime::class)
private fun getExpiryText(expiryInfo: ProductExpiryInfo): String {
    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    val currentYear = localDateTime.year
    val expiryMonth = expiryInfo.expiryMonth
    val expiryDay = expiryInfo.expiryDay

    return expiryInfo.expiryYear?.let { expiryYear ->
        when {
            currentYear > expiryYear -> "Expired"

            currentYear < expiryYear -> if (expiryMonth != null && expiryMonth in 1..12) {
                "Expires in ${Month(expiryInfo.expiryMonth).name} $expiryYear"
            } else {
                "Expires in $expiryYear"
            }

            currentYear == expiryYear -> if (expiryDay != null && expiryMonth != null && expiryDay in 1..31 && expiryMonth in 1..12) {
                "Expires on ${Month(expiryInfo.expiryMonth).name} $expiryDay"
            } else if (expiryDay == null && expiryMonth != null && expiryMonth in 1..12) {
                "Expires in ${Month(expiryInfo.expiryMonth).name}"
            } else {
                "Expires this year"
            }

            else -> ""
        }
    } ?: ""
}

@OptIn(ExperimentalTime::class)
private fun getExpiredStatus(expiryInfo: ProductExpiryInfo): Boolean {
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
                        expiryDay != null && localDateTime.day >= expiryDay
                    }
                } else {
                    false
                }
            }
            else -> false
        }
    } ?: false
}

@OptIn(ExperimentalTime::class)
private fun getDurationRemaining(expiryInfo: ProductExpiryInfo): String {
    if (getExpiredStatus(expiryInfo)) {
        return getFormattedDaysRemaining(-1)
    }

    val expiryYear = expiryInfo.expiryYear
    val expiryMonth = expiryInfo.expiryMonth
    val expiryDay = expiryInfo.expiryDay

    val now = Clock.System.now()
    val localTimeZone = TimeZone.currentSystemDefault()
    val localDateTime = now.toLocalDateTime(localTimeZone)

    val currentDate = LocalDate(localDateTime.year, localDateTime.month, localDateTime.day)

    return if (expiryYear != null) {
        if (expiryMonth != null && expiryDay != null) {
            val expiryDate = LocalDate(expiryYear, expiryMonth, expiryDay)
            val daysRemaining = currentDate.daysUntil(expiryDate)
            return getFormattedDaysRemaining(daysRemaining)
        } else if (expiryMonth != null && expiryDay == null) {
            val expiryDate = LocalDate(expiryYear, expiryMonth, 1).plus(1, DateTimeUnit.MONTH)
            val daysRemaining = currentDate.daysUntil(expiryDate)
            return getFormattedDaysRemaining(daysRemaining)
        } else {
            ""
        }
    } else {
        return ""
    }
}

private fun getFormattedDaysRemaining(daysRemaining: Int): String {
    if (daysRemaining < 0) {
        return "Expired"
    }

    return if (daysRemaining >= 365 * 2) {
        "${daysRemaining/365} years"
    } else {
        if (daysRemaining >= 60) {
            "${daysRemaining/30} months"
        } else {
            if (daysRemaining == 1) {
                "1 day"
            } else {
                "$daysRemaining days"
            }
        }
    }
}