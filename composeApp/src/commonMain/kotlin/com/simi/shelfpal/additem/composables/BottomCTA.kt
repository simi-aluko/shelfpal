package com.simi.shelfpal.additem.composables

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
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.onPrimaryLight
import com.simi.shelfpal.theme.onSecondaryLight
import com.simi.shelfpal.theme.onTertiaryLight
import com.simi.shelfpal.theme.primaryLight
import com.simi.shelfpal.theme.secondaryLight
import com.simi.shelfpal.theme.tertiaryLight
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
        modifier = Modifier.padding(vertical = Dimens.spacingSmall)
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