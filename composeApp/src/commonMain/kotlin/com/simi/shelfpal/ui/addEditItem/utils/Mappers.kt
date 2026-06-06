package com.simi.shelfpal.ui.addEditItem.utils

import com.simi.shelfpal.data.database.entities.ShelfPalItem
import com.simi.shelfpal.data.models.ProductBasicInfo
import com.simi.shelfpal.data.models.ProductExpiryInfo
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemImage
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState
import com.simi.shelfpal.ui.shared.capitalizeFirstChar
import com.simi.shelfpal.ui.shared.capitalizeFirstCharInWords
import com.simi.shelfpal.ui.shared.getDisplayMonths
import com.simi.shelfpal.utils.getLocalTimestamp
import kotlinx.datetime.Month
import kotlinx.datetime.number
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getShelPalItemFromViewModel(
    addEditItemBasicInfo: AddEditItemViewState.AddEditItemBasicInfo,
    addEditItemExpiryInfo: AddEditItemViewState.AddEditItemExpiryInfo,
    basicInfoImageUrl: String?,
    expiryInfoImageUrl: String?
) = ShelfPalItem(
    productImageUrl = basicInfoImageUrl,
    productExpiryImageUrl = expiryInfoImageUrl,
    createdDate = getLocalTimestamp(),
    basicInfo = ProductBasicInfo(
        name = addEditItemBasicInfo.productName.capitalizeFirstCharInWords(),
        category = addEditItemBasicInfo.category
    ),
    expiryInfo = ProductExpiryInfo(
        expiryDay = addEditItemExpiryInfo.day.toIntOrNull(),
        expiryMonth = Month.entries.find { it.name.capitalizeFirstChar() == addEditItemExpiryInfo.month }?.number,
        expiryYear = addEditItemExpiryInfo.year.toIntOrNull(),
        hasDay = addEditItemExpiryInfo.hasDay
    )
)

fun ShelfPalItem.toEditItemBasicInfo() = AddEditItemViewState.AddEditItemBasicInfo(
    image = AddEditItemImage(
        url = productImageUrl,
        isEdit = true
    ),
    productName = basicInfo.name,
    category = basicInfo.category
)

fun ShelfPalItem.toEditItemExpiryInfo() = AddEditItemViewState.AddEditItemExpiryInfo(
    day = expiryInfo.expiryDay?.toString().orEmpty(),
    month = expiryInfo.expiryMonth?.takeIf { it in 1..12 }?.let { getDisplayMonths()[it - 1] }.orEmpty(),
    year = expiryInfo.expiryYear?.toString().orEmpty(),
    hasDay = expiryInfo.hasDay,
    image = AddEditItemImage(
        url = productExpiryImageUrl,
        isEdit = true
    )
)