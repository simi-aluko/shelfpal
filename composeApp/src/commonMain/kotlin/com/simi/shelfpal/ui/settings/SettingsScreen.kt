package com.simi.shelfpal.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Colors
import androidx.compose.material.Typography
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.notification_subtitle
import shelfpal.composeapp.generated.resources.notifications_24px
import shelfpal.composeapp.generated.resources.push_notifications

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Settings screen")
    }
}

@Composable
private fun NotificationSettings(
    icon: DrawableResource,
    title: StringResource,
    subtitle: StringResource,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit = {}
) {
    DefaultCard {
        Row(
            modifier = Modifier.padding(Dimens.spacingSmallMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resource = icon),
                contentDescription = null,
                tint = primaryLight
            )
            Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(title),
                    fontSize = Dimens.textSizeMedium
                )
                Text(
                    text = stringResource(subtitle),
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

@Composable
private fun ExportExpiryData() {

}

@Composable
private fun ProvideFeedback() {

}

@Composable
private fun CreateAccount() {

}

@Composable
private fun AppInformation() {

}

@Preview
@Composable
private fun NotificationSettingsPreview() {
    NotificationSettings(
        icon = Res.drawable.notifications_24px,
        title = Res.string.push_notifications,
        subtitle = Res.string.notification_subtitle,
        checked = false,
        onCheckChanged = {}
    )
}