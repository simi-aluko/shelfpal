package com.simi.shelfpal.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.simi.shelfpal.ui.shared.composables.TopBar

@Composable
fun WebviewScreen(
    title: String,
    url: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = title,
                onNavigateBack = onNavigateBack,
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            val state = rememberWebViewState(url)
            val loadingState = state.loadingState

            Column {
                if (loadingState is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = { loadingState.progress },
                        modifier = Modifier.fillMaxWidth(),
                        color = ProgressIndicatorDefaults.linearColor,
                        trackColor = ProgressIndicatorDefaults.linearTrackColor,
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )
                }

                WebView(state)
            }
        }
    }
}