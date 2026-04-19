package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import com.simi.shelfpal.data.models.ShelfPalItemReminderSettings
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
fun ItemDetailSettings(
    data: ShelfPalItemReminderSettings,
    onCheckClick: (ShelfPalItemReminderSettings) -> Unit,
    enabled: Boolean = true,
) {
    ItemDetailCard {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium)
        ) {
            ItemDetailSectionHeader(stringResource(Res.string.reminder_settings))
            Spacer(modifier = Modifier.size(Dimens.spacingSmall))
            ItemDetailSettingsBody(
                data = data,
                onCheckClick = onCheckClick,
                enabled = enabled,
            )
        }
    }
}

@Composable
private fun ItemDetailSettingsBody(
    data: ShelfPalItemReminderSettings,
    onCheckClick: (ShelfPalItemReminderSettings) -> Unit,
    enabled: Boolean = true,
) {
    val showCustomReminder = data.customDaysBefore != null

    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
    ) {
        ItemDetailsReminderRow(
            checked = data.notifyMonthBefore,
            title = stringResource(Res.string.one_month_before),
            subtitle = stringResource(Res.string.get_notified_one_month_before),
            iconTint = tertiaryLight,
            hasExpired = data.hasExpired,
            enabled = enabled,
            onChecked = {
                onCheckClick(data.copy(notifyMonthBefore = it))
            }
        )
        ItemDetailsReminderRow(
            checked = data.notifyWeekBefore,
            title = stringResource(Res.string.one_week_before),
            subtitle = stringResource(Res.string.get_notified_one_week_before),
            iconTint = colorOrange,
            hasExpired = data.hasExpired,
            enabled = enabled,
            onChecked = {
                onCheckClick(data.copy(notifyWeekBefore = it))
            }
        )
        ItemDetailsReminderRow(
            checked = data.notifyOnDay,
            title = stringResource(Res.string.on_expiry),
            subtitle = stringResource(Res.string.get_notified_on_expiry_date),
            iconTint = errorLight,
            hasExpired = data.hasExpired,
            enabled = enabled,
            onChecked = {
                onCheckClick(data.copy(notifyOnDay = it))
            }
        )
        ItemDetailsReminderRow(
            checked = showCustomReminder,
            title = stringResource(Res.string.custom_reminder),
            subtitle = stringResource(Res.string.set_custom_reminder),
            iconTint = Color.Gray,
            hasExpired = data.hasExpired,
            enabled = enabled,
            onChecked = {
                if (it) {
                    onCheckClick(data.copy(customDaysBefore = 1))
                } else {
                    onCheckClick(data.copy(customDaysBefore = null))
                }
            }
        )
        if (showCustomReminder) {
            CustomReminder(
                days = data.customDaysBefore,
                onValueChange = {
                    onCheckClick(data.copy(customDaysBefore = it))
                }
            )
        }
    }
}

@Composable
private fun ItemDetailsReminderRow(
    checked: Boolean?,
    title: String,
    subtitle: String,
    iconTint: Color,
    hasExpired: Boolean,
    enabled: Boolean = true,
    onChecked: (Boolean) -> Unit,
) {

    var checked = checked

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
            checked = checked ?: false,
            enabled = enabled && checked != null && !hasExpired,
            onCheckedChange = {
                checked = it
                onChecked(it)
            }
        )
    }
}

@Composable
private fun CustomReminder(
    days: Int?,
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableStateOf(days) }

    Column {
        Text(
            text = stringResource(Res.string.custom_reminder_days_before),
            fontSize = Dimens.textSizeMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(Dimens.spacingSmall))
        TextField(
            value = value.toString(),
            onValueChange = {
                val updatedDays = it.toIntOrNull() ?: 1
                if (it.length <= 4 && updatedDays > 0) {
                    value = updatedDays
                    onValueChange(updatedDays)
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
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}