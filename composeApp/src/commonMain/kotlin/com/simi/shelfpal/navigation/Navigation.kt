package com.simi.shelfpal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simi.shelfpal.ui.additem.AddItemScreen
import com.simi.shelfpal.ui.home.HomeScreen
import com.simi.shelfpal.ui.imagecapture.ImageCaptureScreen
import com.simi.shelfpal.ui.welcome.WelcomeScreen
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Welcome : Routes()

    @Serializable
    object Home : Routes()

    @Serializable
    object AddItem : Routes()

    @Serializable
    object ImageCapture : Routes()
}

@Composable
fun ShelfPalApp(
    navHostController: NavHostController = rememberNavController(),
    startDestination: Routes = Routes.Welcome
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<Routes.Welcome> {
            WelcomeScreen(
                content = _root_ide_package_.com.simi.shelfpal.ui.welcome.carouselContent,
                navigateToHome = {
                    navHostController.navigate(Routes.Home)
                }
            )
        }
        composable<Routes.Home> {
            HomeScreen {
                navHostController.navigate(Routes.AddItem)
            }

        }
        composable<Routes.AddItem> {
            AddItemScreen(
                navigateBack = { navHostController.popBackStack() },
                navigateToImageCapture = { navHostController.navigate(Routes.ImageCapture) },
                navController = navHostController
            )
        }
        composable<Routes.ImageCapture> {
            ImageCaptureScreen(
                navController = navHostController
            )
        }
    }
}