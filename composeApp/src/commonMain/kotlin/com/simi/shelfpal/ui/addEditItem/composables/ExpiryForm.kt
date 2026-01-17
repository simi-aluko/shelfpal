package com.simi.shelfpal.ui.addEditItem.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.addEditItem.AddItemBottomSheetType
import com.simi.shelfpal.ui.addEditItem.models.AddEditItemViewState.AddEditItemExpiryInfo
import com.simi.shelfpal.ui.shared.composables.ReadOnlyTextField
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_expiry_day
import shelfpal.composeapp.generated.resources.day
import shelfpal.composeapp.generated.resources.expiry_info
import shelfpal.composeapp.generated.resources.month
import shelfpal.composeapp.generated.resources.select_expiry_day
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiryForm(
    formData: AddEditItemExpiryInfo,
    onExpiryDateClick: (AddItemBottomSheetType) -> Unit,
    onDayChecked: (Boolean) -> Unit,
    onCameraClick: () -> Unit
) {
    OutlinedCard(
        border = BorderStroke(1.dp, onPrimaryContainerLight)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.spacingSmallMedium)
        ) {
            FormHeader(
                title = stringResource(Res.string.expiry_info),
                onCameraClick = onCameraClick
            )
            ExpirySection(
                sectionData = formData,
                onExpiryDayClick = {
                    onExpiryDateClick(AddItemBottomSheetType.DAY)
                },
                onExpiryMonthClick = {
                    onExpiryDateClick(AddItemBottomSheetType.MONTH)
                },
                onExpiryYearClick = {
                    onExpiryDateClick(AddItemBottomSheetType.YEAR)
                }
            )
            AddDayCheckBox(
                checked = formData.hasDay,
                onCheckChanged = { onDayChecked(it) }
            )
            AddEditItemImageComposable(formData.image)
        }
    }
}

@Composable
private fun ExpirySection(
    sectionData: AddEditItemExpiryInfo,
    onExpiryMonthClick: () -> Unit,
    onExpiryYearClick: () -> Unit,
    onExpiryDayClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        if (sectionData.hasDay) {
            ReadOnlyTextField(
                value = sectionData.day,
                label = stringResource(Res.string.day),
                placeholder = stringResource(Res.string.select_expiry_day),
                onClick = onExpiryDayClick,
                modifier = Modifier.weight(1f),
                showTrailingIcon = !sectionData.hasDay,
                isError = sectionData.expiryInfoError.dayError,
                errorText = stringResource(sectionData.expiryInfoError.dayErrorText),
            )
        }
        ReadOnlyTextField(
            value = sectionData.month,
            label = stringResource(Res.string.month),
            placeholder = stringResource(Res.string.select_expiry_month),
            onClick = onExpiryMonthClick,
            modifier = Modifier.weight(1f),
            showTrailingIcon = !sectionData.hasDay,
            isError = sectionData.expiryInfoError.monthError,
            errorText = stringResource(sectionData.expiryInfoError.monthErrorText),
        )
        ReadOnlyTextField(
            value = sectionData.year,
            label = stringResource(Res.string.year),
            placeholder = stringResource(Res.string.select_expiry_year),
            onClick = onExpiryYearClick,
            modifier = Modifier.weight(1f),
            showTrailingIcon = !sectionData.hasDay,
            isError = sectionData.expiryInfoError.yearError,
            errorText = stringResource(sectionData.expiryInfoError.yearErrorText),
        )
    }
}

@Composable
private fun AddDayCheckBox(
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = Dimens.spacingSmallMedium)
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckChanged
            )
        }
        Spacer(modifier = Modifier.size(Dimens.spacingExtraSmall))
        Text(
            text = stringResource(Res.string.add_expiry_day)
        )
    }
}