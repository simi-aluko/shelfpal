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
import com.simi.shelfpal.navigation.Route.Webview
import com.simi.shelfpal.ui.settings.composables.AppExperience
import com.simi.shelfpal.ui.settings.composables.AppInformation
import com.simi.shelfpal.ui.settings.composables.NotificationSettings
import com.simi.shelfpal.ui.shared.composables.TopBar
import com.simi.shelfpal.ui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.give_feedback
import shelfpal.composeapp.generated.resources.report_bug
import shelfpal.composeapp.generated.resources.settings

private const val feedbackUrl = "https://forms.gle/bwuT4hA57jcwp9p9A"
private const val bugReportUrl = "https://forms.gle/7pHMnbFjVX7m73od9"

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    launchWebview: (Webview) -> Unit = {},
    viewModel: SettingsViewModel = viewModel { SettingsViewModel() }
) {

    val notificationToggle by viewModel.notificationToggle.collectAsStateWithLifecycle()
    val feedbackTitle = stringResource(Res.string.give_feedback)
    val reportBugTitle = stringResource(Res.string.report_bug)

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
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = Dimens.spacingSmallMedium,
                        end = Dimens.spacingSmallMedium,
                        top = Dimens.spacingMedium
                    ),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
            ) {
                NotificationSettings(
                    checked = notificationToggle,
                    onCheckChanged = {
                        viewModel.toggleNotification(it)
                    }
                )
                AppExperience(
                    onGiveFeedback = {
                        launchWebview(
                            Webview(
                                title = feedbackTitle,
                                url = feedbackUrl
                            )
                        )
                    },
                    onReportBug = {
                        launchWebview(
                            Webview(
                                title = reportBugTitle,
                                url = bugReportUrl
                            )
                        )
                    },
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