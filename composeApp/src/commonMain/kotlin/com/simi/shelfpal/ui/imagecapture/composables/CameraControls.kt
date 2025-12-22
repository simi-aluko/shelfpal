package com.simi.shelfpal.ui.imagecapture.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import com.kashif.cameraK.controller.CameraController
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.onTertiaryContainerLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.camera
import shelfpal.composeapp.generated.resources.capture
import shelfpal.composeapp.generated.resources.flashoff
import shelfpal.composeapp.generated.resources.flashon
import shelfpal.composeapp.generated.resources.torchoff
import shelfpal.composeapp.generated.resources.torchon
import shelfpal.composeapp.generated.resources.turn_off_flash
import shelfpal.composeapp.generated.resources.turn_off_torch
import shelfpal.composeapp.generated.resources.turn_on_flash
import shelfpal.composeapp.generated.resources.turn_on_torch

@Composable
fun CameraControls(
    cameraController: CameraController,
    imageBitmap: ImageBitmap?,
    onDismissImagePreview: () -> Unit,
    onCapture: () -> Unit
) {
    var isFlashOn by remember { mutableStateOf(false) }
    var isTorchOn by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        _root_ide_package_.com.simi.shelfpal.ui.imagecapture.composables.BottomControls(
            modifier = Modifier.align(Alignment.BottomCenter),
            onCapture = onCapture,
            flashOn = isFlashOn,
            torchOn = isTorchOn,
            onFlashToggle = {
                isFlashOn = !isFlashOn
                cameraController.toggleFlashMode()
            },
            onTorchToggle = {
                isTorchOn = !isTorchOn
                cameraController.toggleTorchMode()
            }
        )

        _root_ide_package_.com.simi.shelfpal.ui.imagecapture.composables.ImagePreview(
            imageBitmap = imageBitmap,
            onDismiss = onDismissImagePreview
        )
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier = Modifier,
    flashOn: Boolean,
    torchOn: Boolean,
    onCapture: () -> Unit,
    onFlashToggle: () -> Unit,
    onTorchToggle: () -> Unit
) {
    val (torchIcon, torchContentDescription) = if (torchOn) {
        Pair(Res.drawable.torchon, Res.string.turn_off_torch)
    } else {
        Pair(Res.drawable.torchoff, Res.string.turn_on_torch)
    }
    val (flashIcon, flashContentDescription) = if (flashOn) {
        Pair(Res.drawable.flashon, Res.string.turn_off_flash)
    } else {
        Pair(Res.drawable.flashoff, Res.string.turn_on_flash)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.spacingXLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMedium)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        _root_ide_package_.com.simi.shelfpal.ui.imagecapture.composables.CameraControlItem(
            onClick = onTorchToggle,
            buttonSize = Dimens.avatarSizeLarge,
            iconSize = Dimens.iconSizeMedium,
            containerColor = onTertiaryContainerLight.copy(alpha = 0.6f),
            icon = torchIcon,
            contentDescription = torchContentDescription
        )
        _root_ide_package_.com.simi.shelfpal.ui.imagecapture.composables.CameraControlItem(
            onClick = onCapture,
            buttonSize = Dimens.buttonHeightLarge,
            iconSize = Dimens.iconSizeLarge,
            containerColor = onPrimaryContainerLight,
            icon = Res.drawable.camera,
            contentDescription = Res.string.capture
        )
        _root_ide_package_.com.simi.shelfpal.ui.imagecapture.composables.CameraControlItem(
            onClick = onFlashToggle,
            buttonSize = Dimens.avatarSizeLarge,
            iconSize = Dimens.iconSizeMedium,
            containerColor = onTertiaryContainerLight.copy(alpha = 0.6f),
            icon = flashIcon,
            contentDescription = flashContentDescription
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun CameraControlItem(
    buttonSize: Dp,
    iconSize: Dp,
    containerColor: Color,
    icon: DrawableResource,
    contentDescription: StringResource,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier
            .size(buttonSize)
            .clip(CircleShape),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = containerColor,
        )
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = stringResource(contentDescription),
            tint = onPrimaryLight,
            modifier = Modifier.size(iconSize)
        )
    }
}