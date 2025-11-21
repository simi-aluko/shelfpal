package com.simi.shelfpal.additem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simi.shelfpal.shared.ReadOnlyTextField
import com.simi.shelfpal.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.expiry_info
import shelfpal.composeapp.generated.resources.month
import shelfpal.composeapp.generated.resources.select_expiry_month
import shelfpal.composeapp.generated.resources.select_expiry_year
import shelfpal.composeapp.generated.resources.year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiryForm(
    expiryMonth: String = "",
    expiryYear: String = "",
    onExpiryMonthClick: () -> Unit = {},
    onExpiryYearClick: () -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.size(Dimens.spacingMediumLarge))
        FormHeader(
            title = stringResource(Res.string.expiry_info),
            onCameraClick = {}
        )
        ExpirySection(
            expiryMonth = expiryMonth,
            expiryYear = expiryYear,
            onExpiryMonthClick = onExpiryMonthClick,
            onExpiryYearClick = onExpiryYearClick
        )
        Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
    }
}

@Composable
private fun ExpirySection(
    expiryMonth: String,
    expiryYear: String,
    onExpiryMonthClick: () -> Unit,
    onExpiryYearClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSmall)
    ) {
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