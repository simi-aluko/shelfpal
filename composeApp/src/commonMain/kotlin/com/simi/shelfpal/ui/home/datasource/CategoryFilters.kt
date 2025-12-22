package com.simi.shelfpal.ui.home.datasource

/*val categoryFilters = listOf(
    Category(
        title = Res.string.all_items,
        leftIcon = Res.drawable.apps,
    ),
    Category(
        title = Res.string.category_fresh_fruits_and_vegetables,
        leftIcon = Res.drawable.vegetable,
    ),
    Category(
        title = Res.string.category_meats_dairy_and_frozen_foods,
        leftIcon = Res.drawable.food,
    ),
    Category(
        title = Res.string.category_pastry_and_staples,
        leftIcon = Res.drawable.bread,
    ),
    Category(
        title = Res.string.category_canned_foods_oils_sauces_and_spices,
        leftIcon = Res.drawable.spices,
    ),
    Category(
        title = Res.string.category_beverages,
        leftIcon = Res.drawable.drinks,
    ),
    Category(
        title = Res.string.category_prepared_meals,
        leftIcon = Res.drawable.seafood,
    ),
    Category(
        title = Res.string.category_medications_and_vitamins,
        leftIcon = Res.drawable.medicine,
    ),
    Category(
        title = Res.string.category_personal_care_and_beauty,
        leftIcon = Res.drawable.personalcare,
    ),
    Category(
        title = Res.string.category_cleaning_and_household,
        leftIcon = Res.drawable.cleaningtools,
    ),
    Category(
        title = Res.string.category_infant_and_pet_care,
        leftIcon = Res.drawable.petcare,
    ),
    Category(
        title = Res.string.other,
        leftIcon = Res.drawable.selectall,
    ),
)*/


/**
 *                 TopRowFilter(
 *                     filters = setOf(
 *                         Category(
 *                             title = stringResource(Res.string.all_items),
 *                             leftIcon = Res.drawable.apps,
 *                             onClick = { showBottomSheet = true },
 *                             rightIcon = Res.drawable.chevrondown
 *                         ),
 *                         Filter.Month(
 *                             title = "January",
 *                             onClick = { showBottomSheet = true },
 *                             rightIcon = Res.drawable.chevrondown
 *                         ),
 *                         Filter.Year(
 *                             title = "2025",
 *                             onClick = { showBottomSheet = true },
 *                             rightIcon = Res.drawable.chevrondown
 *                         )
 *                     )
 *                 )
 *                 Spacer(modifier = Modifier.size(Dimens.spacingMediumLarge))
 *                 ShelfItems(
 *                     items = listOf(
 *                         ShelfItem(
 *                             id = 2,
 *                             productName = "Protein",
 *                             productImage = Res.drawable.food,
 *                             expiryText = "Expires Nov 4",
 *                             expired = true,
 *                             durationRemaining = "Expired"
 *                         ),
 *                         ShelfItem(
 *                             id = 1,
 *                             productName = "Milk",
 *                             productImage = Res.drawable.vegetable,
 *                             expiryText = "Expires Nov 4",
 *                             expired = false,
 *                             durationRemaining = "2 days"
 *                         ),
 *                         ShelfItem(
 *                             id = 1,
 *                             productName = "Milk",
 *                             productImage = Res.drawable.vegetable,
 *                             expiryText = "Expires Nov 4",
 *                             expired = false,
 *                             durationRemaining = "14 days"
 *                         ),
 *                     )
 *                 )
 *                 FilterBottomSheet(
 *                     sheetState = sheetState,
 *                     showBottomSheet = showBottomSheet,
 *                     bottomSheetHeaderViewModel = BottomSheetHeaderViewModel(
 *                         title = "Filter by Category",
 *                         subtitle = "Choose a category to filter your items"
 *                     ),
 *                     filters = listOf(
 *                         Category(
 *                             title = stringResource(Res.string.all_items),
 *                             leftIcon = Res.drawable.apps,
 *                             selected = true,
 *                             rightIcon = Res.drawable.check_24px,
 *                             onClick = {
 *                                 scope.launch { sheetState.hide() }
 *                                 showBottomSheet = false
 *                             }
 *                         ),
 *                         Category(
 *                             title = stringResource(Res.string.all_items),
 *                             leftIcon = Res.drawable.apps,
 *                             selected = false,
 *                             rightIcon = null,
 *                             onClick = {
 *                                 scope.launch { sheetState.hide() }
 *                                 showBottomSheet = false
 *                             }
 *                         ),
 *                         Category(
 *                             title = stringResource(Res.string.all_items),
 *                             leftIcon = Res.drawable.apps,
 *                             selected = false,
 *                             rightIcon = null,
 *                             onClick = {
 *                                 scope.launch { sheetState.hide() }
 *                                 showBottomSheet = false
 *                             }
 *                         )
 *                     ),
 *                     onDismiss = { showBottomSheet = false }
 *                 )
 */