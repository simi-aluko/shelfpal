package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.colorOrange
import com.simi.shelfpal.ui.theme.errorLight
import com.simi.shelfpal.ui.theme.surfaceContainerHighestLight
import com.simi.shelfpal.ui.theme.surfaceContainerLight
import com.simi.shelfpal.ui.theme.tertiaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.custom_reminder
import shelfpal.composeapp.generated.resources.custom_reminder_days_before
import shelfpal.composeapp.generated.resources.get_notified_on_expiry_date
import shelfpal.composeapp.generated.resources.get_notified_one_month_before
import shelfpal.composeapp.generated.resources.get_notified_one_week_before
import shelfpal.composeapp.generated.resources.notification
import shelfpal.composeapp.generated.resources.number_of_days
import shelfpal.composeapp.generated.resources.on_expiry
import shelfpal.composeapp.generated.resources.one_month_before
import shelfpal.composeapp.generated.resources.one_week_before
import shelfpal.composeapp.generated.resources.reminder_settings
import shelfpal.composeapp.generated.resources.set_custom_reminder

@Composable
fun ItemDetailSettings() {
    ItemDetailCard {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium)
        ) {
            ItemDetailSectionHeader(stringResource(Res.string.reminder_settings))
            Spacer(modifier = Modifier.size(Dimens.spacingSmall))
            ItemDetailSettingsBody()
        }
    }
}

@Composable
private fun ItemDetailSettingsBody() {
    var customReminderSet by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        ItemDetailsReminderRow(
            title = stringResource(Res.string.one_month_before),
            subtitle = stringResource(Res.string.get_notified_one_month_before),
            iconTint = tertiaryLight
        )
        ItemDetailsReminderRow(
            title = stringResource(Res.string.one_week_before),
            subtitle = stringResource(Res.string.get_notified_one_week_before),
            iconTint = colorOrange
        )
        ItemDetailsReminderRow(
            title = stringResource(Res.string.on_expiry),
            subtitle = stringResource(Res.string.get_notified_on_expiry_date),
            iconTint = errorLight
        )
        ItemDetailsReminderRow(
            title = stringResource(Res.string.custom_reminder),
            subtitle = stringResource(Res.string.set_custom_reminder),
            iconTint = Color.Gray,
            onChecked = {
                customReminderSet = !customReminderSet
            }
        )
        if (customReminderSet) {
            CustomReminder()
        }
    }
}

@Composable
private fun ItemDetailsReminderRow(
    title: String,
    subtitle: String,
    iconTint: Color,
    onChecked: () -> Unit = {}
) {

    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.notification),
            contentDescription = null,
            tint = iconTint
        )
        Spacer(modifier = Modifier.size(Dimens.spacingExtraSmall))
        Column {
            Text(
                text = title,
                lineHeight = Dimens.textSizeMedium,
                fontSize = Dimens.textSizeMedium
            )
            Text(
                text = subtitle,
                color = Color.Gray,
                fontSize = Dimens.textSizeSmall,
                lineHeight = Dimens.textSizeXSmall
            )
        }
        Spacer(modifier = Modifier.size(Dimens.spacingMedium))
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onChecked()
            }
        )
    }
}

@Composable
private fun CustomReminder() {
    var value by remember { mutableStateOf("") }
    Column {
        Text(
            text = stringResource(Res.string.custom_reminder_days_before),
            fontSize = Dimens.textSizeMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        TextField(
            value = value,
            onValueChange = {
                if (it.length <= 4) {
                    value = it
                }
            },
            placeholder = {
                Text(
                    text = stringResource(Res.string.number_of_days),
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = Dimens.textSizeSmall,
                lineHeight = Dimens.textSizeSmall,
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = surfaceContainerLight,
                unfocusedContainerColor = surfaceContainerLight,
                focusedIndicatorColor = surfaceContainerHighestLight,
                unfocusedIndicatorColor = surfaceContainerHighestLight
            )
        )
    }
}