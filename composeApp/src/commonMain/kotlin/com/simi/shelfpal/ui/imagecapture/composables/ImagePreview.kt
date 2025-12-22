package com.simi.shelfpal.ui.imagecapture.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.scrimLight
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.close

@Composable
fun ImagePreview(
    imageBitmap: ImageBitmap?,
    onDismiss: () -> Unit
) {
    imageBitmap?.let { bitmap ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = scrimLight.copy(alpha = 0.9f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.spacingMedium),
                    contentScale = ContentScale.Fit
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(Dimens.spacingMedium)
                        .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.close),
                        contentDescription = "Close Preview",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        LaunchedEffect(bitmap) {
            delay(3000)
            onDismiss()
        }
    }
}
