package com.simi.shelfpal.ui.welcome

import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.carousel_body_1
import shelfpal.composeapp.generated.resources.carousel_body_2
import shelfpal.composeapp.generated.resources.carousel_body_3
import shelfpal.composeapp.generated.resources.carousel_body_4
import shelfpal.composeapp.generated.resources.carousel_cta_1
import shelfpal.composeapp.generated.resources.carousel_cta_2
import shelfpal.composeapp.generated.resources.carousel_cta_3
import shelfpal.composeapp.generated.resources.carousel_cta_4
import shelfpal.composeapp.generated.resources.carousel_subtitle_1
import shelfpal.composeapp.generated.resources.carousel_subtitle_2
import shelfpal.composeapp.generated.resources.carousel_subtitle_3
import shelfpal.composeapp.generated.resources.carousel_subtitle_4
import shelfpal.composeapp.generated.resources.carousel_title_1
import shelfpal.composeapp.generated.resources.carousel_title_2
import shelfpal.composeapp.generated.resources.carousel_title_3
import shelfpal.composeapp.generated.resources.carousel_title_4
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.packed_closed_check
import shelfpal.composeapp.generated.resources.person_celebrate_24px
import shelfpal.composeapp.generated.resources.view_in_ar_24px

val carouselContent = listOf(
    _root_ide_package_.com.simi.shelfpal.ui.welcome.CarouselPage(
        imageRes = Res.drawable.packed_closed_check,
        title = Res.string.carousel_title_1,
        subtitle = Res.string.carousel_subtitle_1,
        body = Res.string.carousel_body_1,
        ctaTitle = Res.string.carousel_cta_1
    ),
    _root_ide_package_.com.simi.shelfpal.ui.welcome.CarouselPage(
        imageRes = Res.drawable.view_in_ar_24px,
        title = Res.string.carousel_title_2,
        subtitle = Res.string.carousel_subtitle_2,
        body = Res.string.carousel_body_2,
        ctaTitle = Res.string.carousel_cta_2
    ),
    _root_ide_package_.com.simi.shelfpal.ui.welcome.CarouselPage(
        imageRes = Res.drawable.notifications_24px,
        title = Res.string.carousel_title_3,
        subtitle = Res.string.carousel_subtitle_3,
        body = Res.string.carousel_body_3,
        ctaTitle = Res.string.carousel_cta_3
    ),
    _root_ide_package_.com.simi.shelfpal.ui.welcome.CarouselPage(
        imageRes = Res.drawable.person_celebrate_24px,
        title = Res.string.carousel_title_4,
        subtitle = Res.string.carousel_subtitle_4,
        body = Res.string.carousel_body_4,
        ctaTitle = Res.string.carousel_cta_4
    )
)