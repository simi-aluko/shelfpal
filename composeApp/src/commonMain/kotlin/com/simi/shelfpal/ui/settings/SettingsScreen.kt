package com.simi.shelfpal.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simi.shelfpal.ui.settings.composables.AppExperience
import com.simi.shelfpal.ui.settings.composables.AppInformation
import com.simi.shelfpal.ui.settings.composables.ExportExpiryData
import com.simi.shelfpal.ui.settings.composables.NotificationSettings
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.settings

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onGiveFeedback: () -> Unit = {},
    onReportBug: () -> Unit = {},
    viewModel: SettingsViewModel = viewModel()
) {

    val notificationToggle by viewModel.notificationToggle.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.settings),
                onNavigateBack = onNavigateBack,
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = Dimens.spacingSmallMedium,
                        vertical = Dimens.spacingMedium
                    ),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                NotificationSettings(
                    checked = notificationToggle,
                    onCheckChanged = {
                        viewModel.toggleNotification(it)
                    }
                )
                ExportExpiryData {  }
                AppExperience(
                    onGiveFeedback = onGiveFeedback,
                    onReportBug = onReportBug,
                )
                Spacer(modifier = Modifier.weight(1f))
                AppInformation(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreen() {
    SettingsScreen()
}