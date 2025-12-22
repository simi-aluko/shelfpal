package com.simi.shelfpal.ui.shared.models

import org.jetbrains.compose.resources.StringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.category_beverages
import shelfpal.composeapp.generated.resources.category_canned_foods_oils_sauces_and_spices
import shelfpal.composeapp.generated.resources.category_cleaning_and_household
import shelfpal.composeapp.generated.resources.category_fresh_fruits_and_vegetables
import shelfpal.composeapp.generated.resources.category_infant_and_pet_care
import shelfpal.composeapp.generated.resources.category_meats_dairy_and_frozen_foods
import shelfpal.composeapp.generated.resources.category_medications_and_vitamins
import shelfpal.composeapp.generated.resources.category_pastry_and_staples
import shelfpal.composeapp.generated.resources.category_personal_care_and_beauty
import shelfpal.composeapp.generated.resources.category_prepared_meals
import shelfpal.composeapp.generated.resources.other

enum class ProductCategory(val stringResource: StringResource) {
    FRESH_FRUITS_AND_VEGETABLES(Res.string.category_fresh_fruits_and_vegetables),
    MEATS_DAIRY_AND_FROZEN_FOODS(Res.string.category_meats_dairy_and_frozen_foods),
    PASTRY_AND_STAPLES(Res.string.category_pastry_and_staples),
    CANNED_FOODS_OILS_SAUCES_AND_SPICES(Res.string.category_canned_foods_oils_sauces_and_spices),
    BEVERAGES(Res.string.category_beverages),
    PREPARED_MEALS(Res.string.category_prepared_meals),
    MEDICATIONS_AND_VITAMINS(Res.string.category_medications_and_vitamins),
    PERSONAL_CARE_AND_BEAUTY(Res.string.category_personal_care_and_beauty),
    CLEANING_AND_HOUSEHOLD(Res.string.category_cleaning_and_household),
    INFANT_AND_PET_CARE(Res.string.category_infant_and_pet_care),
    OTHER(Res.string.other)
}