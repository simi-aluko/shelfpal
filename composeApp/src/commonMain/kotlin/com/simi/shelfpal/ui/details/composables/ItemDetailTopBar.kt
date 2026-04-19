package com.simi.shelfpal.ui.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.simi.shelfpal.ui.details.models.ItemDetailHeader
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryContainerLight
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ItemDetailTopBar(
     viewData: ItemDetailHeader,
     onNavigationIconClick: () -> Unit,
     onActionIconClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text= viewData.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = Dimens.textSizeMediumLarge,
                    lineHeight = Dimens.textSizeMediumLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text= viewData.subtitle,
                    fontWeight = FontWeight.Normal,
                    fontSize = Dimens.textSizeSmall,
                    lineHeight = Dimens.textSizeMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            if (onActionIconClick != null) {
                viewData.actionIcon?.let {
                    IconButton(
                        onClick = onActionIconClick
                    ) {
                        Icon(
                            imageVector = vectorResource(it),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryContainerLight,
            titleContentColor = primaryLight
        ),
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick
            ) {
                Icon(
                    imageVector = vectorResource(viewData.navigationIcon),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.iconSizeMediumLarge)
                )
            }
        }
    )
}