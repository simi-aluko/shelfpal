package com.simi.shelfpal.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

/**
 * Defines a consistent and extensive set of dimensions, spacing, sizes, and elevations
 * used across the entire application's UI, primarily following an 8-point grid system.
 *
 * Usage: Modifier.padding(Dimens.spacingMedium)
 * Usage: Modifier.size(Dimens.iconSizeLarge)
 */
object Dimens {

    // -------------------------------------------------------------------------
    // 1. SCALING SPACING / MARGINS / PADDING (8-point grid)
    //    Used for distances between components, content padding, and margins.
    // -------------------------------------------------------------------------

    val textSizeXSmall = 12.sp
    val textSizeSmall = 14.sp
    val textSizeMedium = 16.sp
    val textSizeMediumLarge = 18.sp
    val textSizeLarge = 20.sp
    val textSizeXLarge = 24.sp
    val textSizeXXLarge = 28.sp


    /** 0.dp - Explicitly zero spacing. */
    val spacingNone: Dp = 0.dp

    /** 2.dp - Sub-grid spacing for minor adjustments. */
    val spacingMicro: Dp = 2.dp

    /** 4.dp - Extra small spacing (e.g., between an icon and text label). */
    val spacingExtraSmall: Dp = 4.dp

    /** 8.dp - Small spacing (Standard small gap, common for inner padding). */
    val spacingSmall: Dp = 8.dp

    /** 10.dp - A custom step between small and small-medium. */
    val spacingTen: Dp = 10.dp

    /** 12.dp - Between small and medium, useful for slightly tighter layouts. */
    val spacingSmallMedium: Dp = 12.dp

    /** 16.dp - Medium spacing (Standard component margin, screen padding). */
    val spacingMedium: Dp = 16.dp

    /** 20.dp - Useful for slightly larger screen padding or container spacing. */
    val spacingMediumLarge: Dp = 20.dp

    /** 24.dp - Large spacing (Often used for section headings or major component separation). */
    val spacingLarge: Dp = 24.dp

    /** 32.dp - Extra large spacing. */
    val spacingXLarge: Dp = 32.dp

    /** 40.dp - Double Extra large spacing. */
    val spacingXXLarge: Dp = 40.dp

    /** 48.dp - Triple Extra large spacing (e.g., large block separators). */
    val spacingXXXLarge: Dp = 48.dp

    /** 64.dp - Very large spacing for full-screen dividers or major breaks. */
    val spacingHuge: Dp = 64.dp

    // -------------------------------------------------------------------------
    // 2. FIXED COMPONENT SIZES
    //    Used for standardizing the size of specific UI elements.
    // -------------------------------------------------------------------------

    // --- Icon Sizes ---

    /** 16.dp - Smallest standard icon size. */
    val iconSizeSmall: Dp = 16.dp

    /** 24.dp - Default/Medium standard icon size (Material baseline). */
    val iconSizeMedium: Dp = 24.dp

    /** 28.dp - Medium-Large icon size. */
    val iconSizeMediumLarge: Dp = 28.dp

    /** 32.dp - Large icon size (e.g., for major feature icons). */
    val iconSizeLarge: Dp = 32.dp

    /** 36.dp - Extra Large icon size (e.g., for major feature icons). */
    val iconSizeXLarge: Dp = 40.dp

    /** 48.dp - Extra Extra large icon/touch target size. */
    val iconSizeXXLarge: Dp = 48.dp

    // --- Touch Target & Control Sizes ---

    /** 48.dp - Minimum recommended touch target size (Material baseline). */
    val touchTarget: Dp = 48.dp

    /** 48.dp - Standard height for most interactive buttons. */
    val buttonHeight: Dp = 48.dp

    /** 64.dp - Medium height for buttons. */
    val buttonHeightMedium: Dp = 64.dp

    /** 80.dp - Large height for buttons. */
    val buttonHeightLarge: Dp = 80.dp

    /** 32.dp - Height for small/compact buttons or chips. */
    val buttonHeightCompact: Dp = 32.dp

    // --- Image/Avatar Sizes ---

    /** 40.dp - Small avatar/profile image size. */
    val avatarSizeSmall: Dp = 40.dp

    /** 56.dp - Medium avatar/profile image size. */
    val avatarSizeMedium: Dp = 56.dp

    /** 72.dp - Large avatar/profile image size. */
    val avatarSizeLarge: Dp = 72.dp

    // -------------------------------------------------------------------------
    // 3. SHAPES / CORNER RADII
    //    Used for standardizing component rounding.
    // -------------------------------------------------------------------------

    /** 0.dp - No radius, sharp corners. */
    val radiusNone: Dp = 0.dp

    /** 4.dp - Small, subtle corner radius (e.g., small buttons, text fields). */
    val radiusSmall: Dp = 4.dp

    /** 8.dp - Medium standard corner radius (e.g., Cards, Sheets, Dialogs). */
    val radiusMedium: Dp = 8.dp

    /** 12.dp - Large corner radius for a softer look (e.g., Bottom Sheets). */
    val radiusLarge: Dp = 12.dp

    /** 16.dp - Extra large radius. */
    val radiusExtraLarge: Dp = 16.dp

    /** 50.dp - Used with `RoundedCornerShape(Dimens.radiusCircle)` for a circular shape.
     * Note: This value should be equal to or greater than half the size of the component.
     */
    val radiusCircle: Dp = 50.dp

    // -------------------------------------------------------------------------
    // 4. THICKNESS / ELEVATION / DIVIDERS
    //    Used for strokes, borders, and shadows.
    // -------------------------------------------------------------------------

    /** 1.dp - Standard thickness for thin lines or borders. */
    val thicknessHairline: Dp = 1.dp

    /** 2.dp - Thickness for important borders or strokes. */
    val thicknessMedium: Dp = 2.dp

    /** 0.dp - No elevation (flat surface). */
    val elevationNone: Dp = 0.dp

    /** 1.dp - Subtle elevation (e.g., AppBar on Scroll, Cards). */
    val elevationSmall: Dp = 1.dp

    /** 4.dp - Standard elevation for floating elements (e.g., Dialogs, Fab). */
    val elevationMedium: Dp = 4.dp

    /** 8.dp - High elevation for important, modal elements. */
    val elevationLarge: Dp = 8.dp


    // -------------------------------------------------------------------------
    // 5. IMAGE / MEDIA SIZES (Small/Standard Screens)
    // -------------------------------------------------------------------------

    /** 24.dp - Small image/icon size for badges or small thumbnails. */
    val imageSizeExtraSmall: Dp = 24.dp

    /** 40.dp - Standard size for circular avatars/profile pictures in lists. */
    val imageSizeAvatarMedium: Dp = 40.dp

    /** 64.dp - Standard size for circular avatars/profile pictures in lists. */
    val imageSizeAvatarMediumLarge: Dp = 64.dp

    /** 72.dp - Larger size for profile headers or detailed views. */
    val imageSizeAvatarLarge: Dp = 72.dp

    /** 120.dp - Standard width/height for an application logo. */
    val imageSizeLogo: Dp = 120.dp

    /** 200.dp - Height for a feature/hero image in a card or banner. */
    val imageSizeHero: Dp = 200.dp
}