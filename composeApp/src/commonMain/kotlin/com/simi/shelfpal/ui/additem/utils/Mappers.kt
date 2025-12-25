package com.simi.shelfpal.ui.additem.utils

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.additem.models.AddItemViewState
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import kotlinx.datetime.Month
import kotlinx.datetime.number

fun getShelPalItemFromViewModel(
    basicInfo: AddItemViewState.BasicInfo,
    expiryInfo: AddItemViewState.ExpiryInfo
) = ShelfPalItem(
    productImageUrl = "",
    productExpiryImageUrl = "",
    basicInfo = ProductBasicInfo(
        name = basicInfo.productName,
        category = basicInfo.category
    ),
    expiryInfo = ProductExpiryInfo(
        expiryDay = expiryInfo.day.toIntOrNull(),
        expiryMonth = Month.entries.find { it.name.capitalizeFirstChar() == expiryInfo.month }?.number,
        expiryYear = expiryInfo.year.toIntOrNull(),
        hasDay = expiryInfo.hasDay
    )
)
