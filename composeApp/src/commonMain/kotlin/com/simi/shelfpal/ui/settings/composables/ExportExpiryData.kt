package com.simi.shelfpal.ui.settings.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simi.shelfpal.ui.shared.composables.DefaultCard
import com.simi.shelfpal.ui.theme.Dimens
import com.simi.shelfpal.ui.theme.primaryLight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.chart
import shelfpal.composeapp.generated.resources.download
import shelfpal.composeapp.generated.resources.export_shelf_data
import shelfpal.composeapp.generated.resources.export_shelf_data_subtitle

@Composable
fun ExportExpiryData(
    onExport: () -> Unit = {}
) {
    DefaultCard {
        Row(
            modifier = Modifier.padding(Dimens.spacingSmallMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resource = Res.drawable.chart),
                contentDescription = null,
                tint = primaryLight
            )
            Spacer(modifier = Modifier.size(Dimens.spacingSmallMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.export_shelf_data),
                    fontSize = Dimens.textSizeMedium
                )
                Text(
                    text = stringResource(Res.string.export_shelf_data_subtitle),
                    color = Color.Gray,
                    fontSize = Dimens.textSizeSmall
                )
            }
            IconButton(onClick = onExport) {
                Icon(
                    modifier = Modifier.padding(Dimens.spacingSmall),
                    painter = painterResource(resource = Res.drawable.download),
                    contentDescription = null,
                    tint = primaryLight
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExportExpiryDataPreview() {
    ExportExpiryData()
}
