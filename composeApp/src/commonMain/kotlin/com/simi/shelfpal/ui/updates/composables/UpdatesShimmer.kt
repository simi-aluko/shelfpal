package com.simi.shelfpal.ui.updates.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.shared.composables.shimmerBrush
import com.simi.shelfpal.ui.theme.Dimens
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UpdatesShimmer() {
    Column(
        modifier = Modifier.padding(horizontal = Dimens.spacingMedium)
    ) {
        Spacer(modifier = Modifier.height(Dimens.spacingMedium))
        
        // Shelf Summary Shimmer
        ShimmerBox(
            modifier = Modifier
                .width(150.dp)
                .height(Dimens.textSizeLarge.value.dp)
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSmallMedium))
        UpdatesCardShimmer(showRelatedItems = false)

        Spacer(modifier = Modifier.height(Dimens.spacingLarge))

        // Recent Activity Shimmer
        ShimmerBox(
            modifier = Modifier
                .width(180.dp)
                .height(Dimens.textSizeLarge.value.dp)
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSmallMedium))
        Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)) {
            repeat(3) {
                UpdatesCardShimmer()
            }
        }
        Spacer(modifier = Modifier.height(Dimens.spacingMedium))
    }
}

@Composable
fun UpdatesCardShimmer(
    showRelatedItems: Boolean = true
) {
    DefaultCard {
        Column(
            modifier = Modifier.padding(Dimens.spacingSmallMedium)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
            ) {
                // Icon Shimmer
                ShimmerBox(
                    modifier = Modifier.size(Dimens.iconSizeXLarge),
                    shape = CircleShape
                )

                Column(modifier = Modifier.weight(1f)) {
                    // Title Shimmer
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(Dimens.textSizeMedium.value.dp)
                    )

                    Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))

                    // Body Shimmer
                    repeat(2) {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Dimens.textSizeSmall.value.dp)
                        )
                        Spacer(modifier = Modifier.height(Dimens.spacingMicro))
                    }
                }
            }

            if (showRelatedItems) {
                Column(modifier = Modifier.padding(top = Dimens.spacingSmallMedium)) {
                    // Related Items Title Shimmer
                    ShimmerBox(
                        modifier = Modifier
                            .width(80.dp)
                            .height(Dimens.textSizeXSmall.value.dp)
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacingExtraSmall))
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)) {
                        repeat(2) {
                            ShimmerBox(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(30.dp),
                                shape = RoundedCornerShape(Dimens.radiusCircle)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Dimens.radiusSmall)
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(shimmerBrush())
    )
}

@Preview
@Composable
private fun UpdatesShimmerPreview() {
    UpdatesShimmer()
}