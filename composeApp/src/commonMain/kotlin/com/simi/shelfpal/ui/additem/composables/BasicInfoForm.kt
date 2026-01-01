package com.simi.shelfpal.ui.additem.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.additem.models.AddEditItemViewState.AddEditItemBasicInfo
import com.simi.shelfpal.ui.shared.composables.ImageThumbnail
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import com.simi.shelfpal.ui.theme.surfaceContainerHighestLight
import com.simi.shelfpal.ui.theme.surfaceContainerLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.basic_info
import shelfpal.composeapp.generated.resources.chevrondown
import shelfpal.composeapp.generated.resources.enter_the_product_name
import shelfpal.composeapp.generated.resources.product_category
import shelfpal.composeapp.generated.resources.product_name
import shelfpal.composeapp.generated.resources.select_product_category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInfoForm(
    formData: AddEditItemBasicInfo,
    onCategoryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onNameChange: (String) -> Unit
) {
    OutlinedCard(
        border = BorderStroke(1.dp, onPrimaryContainerLight)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Dimens.spacingSmallMedium)
        ) {
            FormHeader(
                title = stringResource(Res.string.basic_info),
                onCameraClick = onCameraClick
            )
            ProductNameTextField(
                productName = formData.productName,
                error = formData.basicInfoError.nameError,
                errorText = stringResource(formData.basicInfoError.nameErrorText),
                onValueChange = { onNameChange(it) }
            )
            ProductCategory(
                selectedCategory = formData.category,
                error = formData.basicInfoError.categoryError,
                errorText = stringResource(formData.basicInfoError.categoryErrorText),
                onClick = onCategoryClick
            )
            formData.imageBitmap?.let {
                ImageThumbnail(it)
            }
        }
    }
}

@Composable
private fun ProductNameTextField(
    productName: String,
    error: Boolean,
    errorText: String,
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    if (productName.isNotEmpty()) {
        value = productName
    }

    TextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = {
            Text(
                text = stringResource(Res.string.product_name),
                color = Color.DarkGray
            )
        },
        placeholder = {
            Text(
                text = stringResource(Res.string.enter_the_product_name),
                color = Color.Gray
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = error,
        supportingText = {
            if (error) {
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
}

@Composable
private fun ProductCategory(
    selectedCategory: String,
    error: Boolean,
    errorText: String,
    onClick: () -> Unit,
) {
    Box {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedCategory,
            onValueChange = { },
            label = {
                Text(
                    text = stringResource(Res.string.product_category),
                    color = Color.Gray
                )
            },
            placeholder = {
                Text(text = stringResource(Res.string.select_product_category))
            },
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = vectorResource(Res.drawable.chevrondown),
                    contentDescription = null
                )
            },
            isError = error,
            supportingText = {
                if (error) {
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

@Composable
@Preview
private fun BasicInfoFormPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium)
        ) {
            BasicInfoForm(
                formData = AddEditItemBasicInfo(
                    imageBitmap = null,
                    productName = "",
                    category = ""
                ),
                onCategoryClick = {},
                onCameraClick = {},
                onNameChange = {}
            )
        }
    }
}