package com.simi.shelfpal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.simi.shelfpal.navigation.Route.AddEditItem
import com.simi.shelfpal.navigation.Route.Home
import com.simi.shelfpal.navigation.Route.ImageCapture
import com.simi.shelfpal.navigation.Route.ItemDetails
import com.simi.shelfpal.navigation.Route.Welcome
import com.simi.shelfpal.ui.addEditItem.AddEditItemScreen
import com.simi.shelfpal.ui.details.ShelfPalItemDetailScreen
import com.simi.shelfpal.ui.home.HomeScreen
import com.simi.shelfpal.ui.imagecapture.ImageCaptureScreen
import com.simi.shelfpal.ui.notifications.NotificationsScreen
import com.simi.shelfpal.ui.settings.SettingsScreen
import com.simi.shelfpal.ui.welcome.WelcomeScreen
import com.simi.shelfpal.ui.welcome.carouselContent

@Composable
fun ShelfPalNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: Route = Welcome,
) {
    NavHost(
        modifier = modifier,
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
                    navHostController.navigate(AddEditItem())
                },
                navigateToItemDetail = { id ->
                    navHostController.navigate(ItemDetails(id))
                },
                navigateToSettings = {
                    navHostController.navigate(Route.Settings)
                }
            )

        }
        composable<AddEditItem> { backStackEntry ->
            val addEditItemRoute = backStackEntry.toRoute<AddEditItem>()

            AddEditItemScreen(
                itemId = addEditItemRoute.id,
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
            val itemId = backStackEntry.toRoute<ItemDetails>().id
            ShelfPalItemDetailScreen(
                id = itemId,
                onNavigationIconClick = { navHostController.popBackStack() },
                onActionIconClick = {
                    navHostController.navigate(AddEditItem(itemId))
                }
            )
        }

        composable<Route.Notifications> {
            NotificationsScreen()
        }

        composable<Route.Settings> {
            SettingsScreen()
        }
    }
}