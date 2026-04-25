package com.simi.shelfpal.ui.shared.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.arrowback

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TopBar(
    title: String,
    subtitle: String? = null,
    onNavigateBack: (() -> Unit)? = null,
    actionIcon: DrawableResource? = null,
    actionIcon2: DrawableResource? = null,
    actionIconClick: () -> Unit = {},
    actionIcon2Click: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            onNavigateBack?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.arrowback),
                        contentDescription = "Back",
                        modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                    )
                }
            }
        },
        title = {
            Column {
                Text(
                    text= title,
                    fontWeight = FontWeight.Bold
                )
                subtitle?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.W400,
                        style = Typography().bodySmall
                    )
                }
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(
                    onClick = actionIconClick
                ) {
                    Icon(
                        imageVector = vectorResource(actionIcon),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                    )
                }
            }
            actionIcon2?.let {
                IconButton(
                    onClick = actionIcon2Click
                ) {
                    Icon(
                        imageVector = vectorResource(actionIcon2),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryContainerLight,
            titleContentColor = primaryLight
        )
    )
}