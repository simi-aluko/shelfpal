package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simi.shelfpal.ui.theme.surfaceContainerHighestLight
import com.simi.shelfpal.ui.theme.surfaceContainerLight
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.chevrondown

@Composable
fun ReadOnlyTextField(
    value: String,
    label: String,
    placeholder: String,
    isError: Boolean,
    errorText: String,
    showTrailingIcon: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier,
    onValueChange: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = label,
                    maxLines = 1,
                    color = Color.Gray
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
            readOnly = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(text = errorText)
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = surfaceContainerLight,
                unfocusedContainerColor = surfaceContainerLight,
                focusedIndicatorColor = surfaceContainerHighestLight,
                unfocusedIndicatorColor = surfaceContainerHighestLight
            )
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