package com.simi.shelfpal

import androidx.compose.runtime.Composable
import com.simi.shelfpal.navigation.ShelfPalApp
import com.simi.shelfpal.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme { ShelfPalApp() }
}