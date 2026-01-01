package com.simi.shelfpal.ui.additem.utils

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.getDisplayMonths
import kotlinx.datetime.Month
import kotlinx.datetime.number

fun getShelPalItemFromViewModel(
    addEditItemBasicInfo: AddEditItemViewState.AddEditItemBasicInfo,
    addEditItemExpiryInfo: AddEditItemViewState.AddEditItemExpiryInfo
) = ShelfPalItem(
    productImageUrl = "",
    productExpiryImageUrl = "",
    basicInfo = ProductBasicInfo(
        name = addEditItemBasicInfo.productName,
        category = addEditItemBasicInfo.category
    ),
    expiryInfo = ProductExpiryInfo(
        expiryDay = addEditItemExpiryInfo.day.toIntOrNull(),
        expiryMonth = Month.entries.find { it.name.capitalizeFirstChar() == addEditItemExpiryInfo.month }?.number,
        expiryYear = addEditItemExpiryInfo.year.toIntOrNull(),
        hasDay = addEditItemExpiryInfo.hasDay
    )
)

fun ProductBasicInfo.toAddEditItemBasicInfo() = AddEditItemViewState.AddEditItemBasicInfo(
    productName = name,
    category = category
)

fun ProductExpiryInfo.toAddEditItemExpiryInfo() = AddEditItemViewState.AddEditItemExpiryInfo(
    day = expiryDay?.toString().orEmpty(),
    month = expiryMonth?.takeIf { it in 1..12 }?.let { getDisplayMonths()[it - 1] }.orEmpty(),
    year = expiryYear?.toString().orEmpty(),
    hasDay = hasDay
)