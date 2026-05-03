package com.simi.shelfpal.ui.settings.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.notification_subtitle
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.push_notifications

@Composable
fun NotificationSettings(
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit = {}
) {
    DefaultCard {
        Row(
            modifier = Modifier.padding(Dimens.spacingSmallMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resource = Res.drawable.notifications_24px),
                contentDescription = null,
                tint = primaryLight
            )
            Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.push_notifications),
                    fontSize = Dimens.textSizeMedium
                )
                Text(
                    text = stringResource(Res.string.notification_subtitle),
                    color = Color.Gray,
                    fontSize = Dimens.textSizeSmall
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckChanged
            )
        }
    }
}

@Preview
@Composable
private fun NotificationSettingsPreview() {
    NotificationSettings(
        checked = false,
        onCheckChanged = {}
    )
}