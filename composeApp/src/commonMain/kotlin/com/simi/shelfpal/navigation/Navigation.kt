package com.simi.shelfpal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.simi.shelfpal.navigation.Routes.AddItem
import com.simi.shelfpal.navigation.Routes.Home
import com.simi.shelfpal.navigation.Routes.ImageCapture
import com.simi.shelfpal.navigation.Routes.ItemDetails
import com.simi.shelfpal.navigation.Routes.Welcome
import com.simi.shelfpal.ui.additem.AddItemScreen
import com.simi.shelfpal.ui.details.ShelfPalItemDetailScreen
import com.simi.shelfpal.ui.home.HomeScreen
import com.simi.shelfpal.ui.imagecapture.ImageCaptureScreen
import com.simi.shelfpal.ui.welcome.WelcomeScreen
import com.simi.shelfpal.ui.welcome.carouselContent
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

    @Serializable
    data class ItemDetails(val id: Long) : Routes()
}

@Composable
fun ShelfPalApp(
    navHostController: NavHostController = rememberNavController(),
    startDestination: Routes = Welcome
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ) {
        composable<Welcome> {
            WelcomeScreen(
                content = carouselContent,
                navigateToHome = {
                    navHostController.navigate(Home)
                }
            )
        }
        composable<Home> {
            HomeScreen(
                navigateToAddItem = {
                    navHostController.navigate(AddItem)
                },
                navigateToItemDetail = { id ->
                    navHostController.navigate(ItemDetails(id))
                }
            )

        }
        composable<AddItem> {
            AddItemScreen(
                navigateBack = { navHostController.popBackStack() },
                navigateToImageCapture = { navHostController.navigate(ImageCapture) },
                navController = navHostController
            )
        }
        composable<ImageCapture> {
            ImageCaptureScreen(
                navController = navHostController
            )
        }

        composable<ItemDetails> { backStackEntry ->
            val itemDetails = backStackEntry.toRoute<ItemDetails>()
            ShelfPalItemDetailScreen(
                id = itemDetails.id,
                onNavigationIconClick = { navHostController.popBackStack() },
                onActionIconClick = {
                    navHostController.navigate(AddItem)
                }
            )
        }
    }
}