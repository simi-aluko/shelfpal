package com.simi.shelfpal.ui.addEditItem.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.camera
import shelfpal.composeapp.generated.resources.launch_camera_to_capture_product

@Composable
fun FormHeader(
    title: String,
    onCameraClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = onPrimaryContainerLight,
            fontSize = Dimens.textSizeMediumLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onCameraClick){
            Icon(
                imageVector = vectorResource(Res.drawable.camera),
                contentDescription = stringResource(
                    Res.string.launch_camera_to_capture_product
                ),
                tint = onPrimaryContainerLight
            )
        }
    }
}