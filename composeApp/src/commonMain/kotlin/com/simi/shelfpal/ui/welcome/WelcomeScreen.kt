package com.simi.shelfpal.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.simi.shelfpal.ui.theme.AppTheme
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.carousel_body_1
import shelfpal.composeapp.generated.resources.carousel_cta_1
import shelfpal.composeapp.generated.resources.carousel_header_image
import shelfpal.composeapp.generated.resources.carousel_subtitle_1
import shelfpal.composeapp.generated.resources.carousel_title_1
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.packed_closed_check
import shelfpal.composeapp.generated.resources.skip

@Composable
fun WelcomeScreen(
    content: List<CarouselPage>,
    navigateToHome: () -> Unit = {}
) {
    Scaffold {
        WelcomeScreenContent(
            content = content,
            navigateToHome = navigateToHome
        )
    }
}

@Composable
private fun WelcomeScreenContent(
    content: List<CarouselPage>,
    navigateToHome: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { content.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMedium)
    ) {
        SkipWelcome { navigateToHome() }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            CarouselContent(content[it])
        }
        CarouselProgressCta(
            ctaTitle = content[pagerState.currentPage].ctaTitle
        ) {
            val nextPage = pagerState.currentPage + 1
            if (nextPage < content.size) {
                scope.launch { pagerState.animateScrollToPage(nextPage) }
            } else if (nextPage == pagerState.pageCount) {
                navigateToHome()
            }
        }
        Spacer(modifier = Modifier.size(Dimens.spacingMedium))
        CarouselPageIndicator(
            pageCount = content.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
private fun CarouselContent(
    content: com.simi.shelfpal.ui.welcome.CarouselPage
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(Dimens.spacingXXLarge))
        CarouselContentHeaderImage(imageRes = content.imageRes)
        Spacer(modifier = Modifier.size(Dimens.spacingXXLarge))
        CarouselContentTitle(title = content.title)
        Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        CarouselContentSubtitle(subtitle = content.subtitle)
        Spacer(modifier = Modifier.size(Dimens.spacingXXLarge))
        CarouselContentBody(body = content.body)
        Spacer(modifier = Modifier.size(Dimens.spacingMedium))
    }
}

@Composable
private fun CarouselProgressCta(ctaTitle: StringResource, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryLight,
            contentColor = onPrimaryLight
        )
    ) {
        Text(
            text = stringResource(ctaTitle),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(Dimens.spacingSmall)
        )
    }
}

@Composable
private fun CarouselContentHeaderImage(
    imageRes: DrawableResource
) {
    ElevatedCard (
        shape = CircleShape,
        modifier = Modifier
            .size(Dimens.imageSizeLogo)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(secondaryContainerLight),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(imageRes),
                modifier = Modifier.size(Dimens.imageSizeAvatarLarge),
                contentDescription = stringResource(Res.string.carousel_header_image),
                colorFilter = ColorFilter.tint(primaryLight)
            )
        }
    }
}

@Composable
private fun CarouselContentTitle(
    title: StringResource
) {
    Text(
        text = stringResource(title),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CarouselContentSubtitle(
    subtitle: StringResource
) {
    Text(
        text = stringResource(subtitle),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CarouselContentBody(
    body: StringResource
) {
    Text(
        text = stringResource(body),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun SkipWelcome(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.spacingSmallMedium),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onClick,
        ) {
            Text(
                text = stringResource(Res.string.skip),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun CarouselPageIndicator(
    pageCount: Int,
    currentPage: Int
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.spacingMedium),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val (color, size) = if (currentPage == iteration) {
                Pair(primaryLight, Dimens.spacingSmallMedium)
            } else {
                Pair(secondaryContainerLight, Dimens.spacingTen)
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.spacingExtraSmall)
                    .clip(CircleShape)
                    .background(color)
                    .size(size)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Preview
@Composable
private fun CarouselContentPreview() {
    AppTheme {
        WelcomeScreen(
            listOf(
                CarouselPage(
                    imageRes = Res.drawable.packed_closed_check,
                    title = Res.string.carousel_title_1,
                    subtitle = Res.string.carousel_subtitle_1,
                    body = Res.string.carousel_body_1,
                    ctaTitle = Res.string.carousel_cta_1
                ),
                CarouselPage(
                    imageRes = Res.drawable.notifications_24px,
                    title = Res.string.carousel_title_1,
                    subtitle = Res.string.carousel_subtitle_1,
                    body = Res.string.carousel_body_1,
                    ctaTitle = Res.string.carousel_cta_1
                )
            )
        )
    }
}

data class CarouselPage(
    val imageRes: DrawableResource,
    val title: StringResource,
    val subtitle: StringResource,
    val body: StringResource,
    val ctaTitle: StringResource
)
