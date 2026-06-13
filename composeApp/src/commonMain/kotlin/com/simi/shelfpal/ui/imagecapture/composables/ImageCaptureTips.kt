package com.simi.shelfpal.ui.imagecapture.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.addEditItem.CameraEntry
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.backgroundLight
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.basic_info_camera_tip
import shelfpal.composeapp.generated.resources.circleinfo
import shelfpal.composeapp.generated.resources.expiry_info_camera_tip

@Composable
fun ImageCaptureTip(
    modifier: Modifier,
    entrySource: CameraEntry
) {
    val tip = when (entrySource) {
        CameraEntry.BASIC_INFO -> Res.string.basic_info_camera_tip
        CameraEntry.EXPIRY_INFO -> Res.string.expiry_info_camera_tip
    }
    Row(
        modifier = modifier
            .background(
                color = backgroundLight.copy(alpha = 0.5f),
                shape = RoundedCornerShape(Dimens.spacingMedium)
            )
            .padding(Dimens.spacingSmall)
            .fillMaxWidth(),
    ) {
        Icon(
            painter = painterResource(Res.drawable.circleinfo),
            contentDescription = null,
            tint = primaryLight
        )
        Spacer(modifier = Modifier.width(Dimens.spacingSmall))
        Text(
            text = stringResource(tip),
            fontWeight = FontWeight.SemiBold,
            color = primaryLight

        )
    }
}

@Composable
@Preview
fun ImageCaptureTipPreview() {
    ImageCaptureTip(
        modifier = Modifier,
        entrySource = CameraEntry.BASIC_INFO
    )
}