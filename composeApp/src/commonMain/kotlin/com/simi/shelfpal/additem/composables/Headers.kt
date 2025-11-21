package com.simi.shelfpal.additem.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.onPrimaryContainerLight
import com.simi.shelfpal.theme.primaryContainerLight
import com.simi.shelfpal.theme.primaryLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_item
import shelfpal.composeapp.generated.resources.camera
import shelfpal.composeapp.generated.resources.close
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