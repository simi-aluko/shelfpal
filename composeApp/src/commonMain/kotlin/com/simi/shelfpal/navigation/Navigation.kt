package com.simi.shelfpal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.simi.shelfpal.navigation.Routes.AddEditItem
import com.simi.shelfpal.navigation.Routes.Home
import com.simi.shelfpal.navigation.Routes.ImageCapture
import com.simi.shelfpal.navigation.Routes.ItemDetails
import com.simi.shelfpal.navigation.Routes.Welcome
import com.simi.shelfpal.ui.addEditItem.AddEditItemScreen
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
    data class AddEditItem(
        val id: Long? = null
    ) : Routes()

    @Serializable
    object ImageCapture : Routes()

    @Serializable
    data class ItemDetails(val id: Long) : Routes()

    @Serializable
    object Notifications : Routes()
}

@Composable
fun ShelfPalApp(
    navHostController: NavHostController = rememberNavController(),
    startDestination: Routes = Welcome
) {
    LaunchedEffect(Unit) {
        NotifierManager.getLocalNotifier().removeAll()
        NotifierManager.addListener(object: NotifierManager.Listener {
            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                navHostController.navigate(Routes.Notifications)
            }
        })

        NotifierManager.setLogger { message ->
            // Log the message
            println("NotifierManager: $message")
        }
    }

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
                    navHostController.navigate(AddEditItem())
                },
                navigateToItemDetail = { id ->
                    navHostController.navigate(ItemDetails(id))
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
    }
}