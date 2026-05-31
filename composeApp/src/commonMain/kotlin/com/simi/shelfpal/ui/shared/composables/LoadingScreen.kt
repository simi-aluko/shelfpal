package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.backgroundLight
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.loading_please_wait

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(backgroundLight.copy(alpha = 0.9f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = primaryLight)
        Spacer(modifier = Modifier.height(Dimens.spacingSmall))
        Text(
            text = stringResource(Res.string.loading_please_wait),
            color = primaryLight,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    Scaffold {
        Surface {
            LoadingScreen()
        }
    }
}