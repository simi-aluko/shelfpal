package com.simi.shelfpal.additem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.simi.shelfpal.shared.ImageThumbnail
import com.simi.shelfpal.shared.ReadOnlyTextField
import com.simi.shelfpal.theme.Dimens
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
    expiryDay: String = "",
    expiryMonth: String = "",
    expiryYear: String = "",
    imageBitmap: ImageBitmap? = null,
    onExpiryDayClick: () -> Unit = {},
    onExpiryMonthClick: () -> Unit,
    onExpiryYearClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    var addDay by remember { mutableStateOf(false) }
    Column {
        Spacer(
            modifier = Modifier.size(Dimens.spacingMediumLarge)
        )
        FormHeader(
            title = stringResource(Res.string.expiry_info),
            onCameraClick = onCameraClick
        )
        ExpirySection(
            expiryDay = expiryDay,
            expiryMonth = expiryMonth,
            expiryYear = expiryYear,
            addDay = addDay,
            onExpiryDayClick = onExpiryDayClick,
            onExpiryMonthClick = onExpiryMonthClick,
            onExpiryYearClick = onExpiryYearClick
        )
        AddDayCheckBox(
            checked = addDay,
            onCheckChanged = { addDay = it }
        )
        Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
        imageBitmap?.let {
            ImageThumbnail(it)
        }
    }
}

@Composable
private fun ExpirySection(
    expiryDay: String,
    expiryMonth: String,
    expiryYear: String,
    addDay: Boolean,
    onExpiryMonthClick: () -> Unit,
    onExpiryYearClick: () -> Unit,
    onExpiryDayClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
        if (addDay) {
            ReadOnlyTextField(
                value = expiryDay,
                label = stringResource(Res.string.day),
                placeholder = stringResource(Res.string.select_expiry_day),
                onClick = onExpiryDayClick,
                modifier = Modifier.weight(1f)
            )
        }
        ReadOnlyTextField(
            value = expiryMonth,
            label = stringResource(Res.string.month),
            placeholder = stringResource(Res.string.select_expiry_month),
            onClick = onExpiryMonthClick,
            modifier = Modifier.weight(1f)
        )
        ReadOnlyTextField(
            value = expiryYear,
            label = stringResource(Res.string.year),
            placeholder = stringResource(Res.string.select_expiry_year),
            onClick = onExpiryYearClick,
            modifier = Modifier.weight(1f)
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckChanged
        )
        Text(
            text = stringResource(Res.string.add_expiry_day)
        )
    }
}