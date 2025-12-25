package com.simi.shelfpal.ui.home.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.onPrimaryContainerLight
import com.simi.shelfpal.ui.theme.onPrimaryLight
import com.simi.shelfpal.ui.theme.primaryLight
import com.simi.shelfpal.ui.theme.secondaryContainerLight
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.add_24px
import shelfpal.composeapp.generated.resources.add_item

@Composable
fun AddItemFAB(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.size(Dimens.imageSizeAvatarLarge),
        onClick = onClick,
        shape = CircleShape,
        colors = IconButtonColors(
            containerColor = primaryLight,
            contentColor = onPrimaryLight,
            disabledContainerColor = secondaryContainerLight,
            disabledContentColor = onPrimaryContainerLight
        )
    ){
        Icon(
            imageVector = vectorResource(Res.drawable.add_24px),
            contentDescription = stringResource(Res.string.add_item)
        )
    }
}