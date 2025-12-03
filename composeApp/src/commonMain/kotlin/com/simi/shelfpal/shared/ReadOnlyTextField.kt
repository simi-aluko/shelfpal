package com.simi.shelfpal.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.chevrondown

@Composable
fun ReadOnlyTextField(
    value: String,
    label: String,
    placeholder: String,
    showTrailingIcon: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            label = {
                Text(
                    text = label,
                    maxLines = 1
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    maxLines = 1
                )
            },
            singleLine = true,
            trailingIcon = if (showTrailingIcon){
                {
                    Icon(
                        imageVector = vectorResource(Res.drawable.chevrondown),
                        contentDescription = null
                    )
                }
            } else {
                null
            },
            readOnly = true
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(
                    onClick = onClick,
                ),
        )
    }
}