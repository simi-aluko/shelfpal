package com.simi.shelfpal.additem.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.onPrimaryContainerLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.basic_info
import shelfpal.composeapp.generated.resources.camera
import shelfpal.composeapp.generated.resources.chevrondown
import shelfpal.composeapp.generated.resources.enter_the_product_name
import shelfpal.composeapp.generated.resources.launch_camera_to_capture_product
import shelfpal.composeapp.generated.resources.product_category
import shelfpal.composeapp.generated.resources.product_name
import shelfpal.composeapp.generated.resources.select_product_category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInfoForm(
    selectedCategory: String,
    onCategoryClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Column {
        FormHeader(
            title = stringResource(Res.string.basic_info),
            onCameraClick = onCameraClick
        )
        ProductNameTextField()
        Spacer(modifier = Modifier.padding(Dimens.spacingSmall))
        ProductCategory(
            selectedCategory = selectedCategory,
            onClick = onCategoryClick
        )
    }
}

@Composable
private fun ProductNameTextField() {
    var value by remember { mutableStateOf("") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = {
            Text(text = stringResource(Res.string.product_name))
        },
        placeholder = {
            Text(text = stringResource(Res.string.enter_the_product_name))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ProductCategory(
    selectedCategory: String,
    onClick: () -> Unit
) {
    Box {
        TextField(
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

