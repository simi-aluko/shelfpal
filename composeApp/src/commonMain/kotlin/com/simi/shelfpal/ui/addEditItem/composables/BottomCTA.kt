package com.simi.shelfpal.ui.addEditItem.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.onSecondaryLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.secondaryLight
import org.jetbrains.compose.resources.stringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.cancel
import shelfpal.composeapp.generated.resources.save_item

@Composable
fun BottomCTA(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.spacingSmall),
        modifier = Modifier.padding(
            top = Dimens.spacingMediumLarge,
            bottom = Dimens.spacingSmall
        )
    ) {
        CTA(
            title = stringResource(Res.string.save_item),
            onClick = onSaveClick,
            containerColor = primaryLight,
            contentColor = onPrimaryLight
        )
        CTA(
            title = stringResource(Res.string.cancel),
            onClick = onCancelClick,
            containerColor = onSecondaryLight,
            contentColor = secondaryLight
        )
    }
}

@Composable
private fun CTA(
    title: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color
) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontSize = Dimens.textSizeMediumLarge,
            modifier = Modifier.padding(Dimens.spacingSmall)
        )
    }
}