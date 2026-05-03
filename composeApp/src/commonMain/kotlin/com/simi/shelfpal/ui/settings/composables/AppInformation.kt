package com.simi.shelfpal.ui.settings.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.version

@Composable
fun AppInformation(version: String = "1.0", modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.version, version),
        color = Color.Gray,
        style = MaterialTheme.typography.bodySmall,
    )
}