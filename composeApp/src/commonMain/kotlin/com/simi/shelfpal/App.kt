package com.simi.shelfpal

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.simi.shelfpal.navigation.ShelfPalApp
import com.simi.shelfpal.theme.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform

@Composable
@Preview
fun App() {
//    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(Unit) {
//        val koin = KoinPlatform.getKoin()
//        val logger = koin.get<ILogger>()
//        val firebaseAi = koin.get<FirebaseConnector>()
//
//        logger.log("Simileoluwa aluko")
//
//        coroutineScope.launch {
//            firebaseAi.generateContent()
//        }
//    }
    AppTheme { ShelfPalApp() }
}