package com.simi.shelfpal.ui.settings.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.surfaceVariantLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.bug
import shelfpal.composeapp.generated.resources.feedback
import shelfpal.composeapp.generated.resources.give_feedback
import shelfpal.composeapp.generated.resources.give_feedback_subtitle
import shelfpal.composeapp.generated.resources.report_bug
import shelfpal.composeapp.generated.resources.report_bug_subtitle

@Composable
fun AppExperience(
    onGiveFeedback: () -> Unit = {},
    onReportBug: () -> Unit = {},
) {
    DefaultCard {
        Column {
            AppExperienceRow(
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.feedback),
                        contentDescription = null,
                        tint = primaryLight
                    )
                },
                title = stringResource(Res.string.give_feedback),
                subtitle = stringResource(Res.string.give_feedback_subtitle),
                onClick = onGiveFeedback,
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = Dimens.spacingSmallMedium),
                color = surfaceVariantLight,
            )
            AppExperienceRow(
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.bug),
                        contentDescription = null,
                        tint = primaryLight
                    )
                },
                title = stringResource(Res.string.report_bug),
                subtitle = stringResource(Res.string.report_bug_subtitle),
                onClick = onReportBug,
            )
        }
    }
}

@Composable
private fun AppExperienceRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(Dimens.spacingSmallMedium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = Dimens.textSizeMedium)
            Text(text = subtitle, color = Color.Gray, fontSize = Dimens.textSizeSmall)
        }
    }
}

@Preview
@Composable
private fun AppExperiencePreview() {
    AppExperience()
}
