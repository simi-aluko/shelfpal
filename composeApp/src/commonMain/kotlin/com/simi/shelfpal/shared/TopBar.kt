package com.simi.shelfpal.shared

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.theme.Dimens
import com.simi.shelfpal.theme.primaryContainerLight
import com.simi.shelfpal.theme.primaryLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TopBar(
    title: String,
    actionIcon: DrawableResource,
    actionIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text= title,
                fontWeight = FontWeight.ExtraBold
            )
        },
        actions = {
            IconButton(
                onClick = actionIconClick
            ) {
                Icon(
                    imageVector = vectorResource(actionIcon),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryContainerLight,
            titleContentColor = primaryLight
        )
    )
}