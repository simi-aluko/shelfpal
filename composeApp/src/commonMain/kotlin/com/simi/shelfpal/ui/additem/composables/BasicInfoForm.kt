package com.simi.shelfpal.ui.additem.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.simi.shelfpal.ui.shared.ImageThumbnail
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryContainerLight
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
    productName: String,
    selectedCategory: String,
    imageBitmap: ImageBitmap? = null,
    onCategoryClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    OutlinedCard(
        border = BorderStroke(2.dp, primaryContainerLight)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium)
        ) {
            FormHeader(
                title = stringResource(Res.string.basic_info),
                onCameraClick = onCameraClick
            )
            ProductNameTextField(
                productName
            )
            Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
            ProductCategory(
                selectedCategory = selectedCategory,
                onClick = onCategoryClick
            )
            Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
            imageBitmap?.let {
                ImageThumbnail(it)
            }
        }
    }
}

@Composable
private fun ProductNameTextField(
    productName: String
) {
    var value by remember { mutableStateOf("") }
    if (productName.isNotEmpty()) {
        value = productName
    }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        label = {
            Text(text = stringResource(Res.string.product_name))
        },
        placeholder = {
            Text(text = stringResource(Res.string.enter_the_product_name))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = true,
        supportingText = { Text(text = "please enter a product name") }
    )
}

@Composable
private fun ProductCategory(
    selectedCategory: String,
    onClick: () -> Unit
) {
    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedCategory,
            onValueChange = { },
            label = {
                Text(text = stringResource(Res.string.product_category))
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
    MaterialTheme(

    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacingMedium)
        ) {
            BasicInfoForm(
                selectedCategory = "Category",
                onCategoryClick = {},
                onCameraClick = {},
                productName = ""
            )
        }
    }
}