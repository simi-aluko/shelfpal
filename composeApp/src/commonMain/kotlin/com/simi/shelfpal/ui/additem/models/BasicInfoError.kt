package com.simi.shelfpal.ui.additem.models

import org.jetbrains.compose.resources.StringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.enter_the_product_name
import shelfpal.composeapp.generated.resources.select_product_category

data class BasicInfoError(
    val nameError: Boolean = false,
    val nameErrorText : StringResource = Res.string.enter_the_product_name,
    val categoryError: Boolean = false,
    val categoryErrorText : StringResource = Res.string.select_product_category
) {
    val hasError: Boolean
        get() = nameError || categoryError
}