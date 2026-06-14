package com.simi.shelfpal.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.simi.shelfpal.navigateToBottomNav
import com.simi.shelfpal.navigation.Route.AddEditItem
import com.simi.shelfpal.navigation.Route.Home
import com.simi.shelfpal.navigation.Route.ImageCapture
import com.simi.shelfpal.navigation.Route.ItemDetails
import com.simi.shelfpal.navigation.Route.Welcome
import com.simi.shelfpal.ui.addEditItem.AddEditItemScreen
import com.simi.shelfpal.ui.addEditItem.CameraEntry
import com.simi.shelfpal.ui.details.ShelfPalItemDetailScreen
import com.simi.shelfpal.ui.home.HomeScreen
import com.simi.shelfpal.ui.home.composables.BottomNavBar
import com.simi.shelfpal.ui.imagecapture.ImageCaptureScreen
import com.simi.shelfpal.ui.settings.SettingsScreen
import com.simi.shelfpal.ui.settings.WebviewScreen
import com.simi.shelfpal.ui.updatedetail.UpdateDetailsEntrySource
import com.simi.shelfpal.ui.updatedetail.UpdatesDetailParams
import com.simi.shelfpal.ui.updatedetail.UpdatesDetailScreen
import com.simi.shelfpal.ui.updates.UpdateRecency
import com.simi.shelfpal.ui.updates.UpdatesScreen
import com.simi.shelfpal.ui.welcome.WelcomeScreen
import com.simi.shelfpal.ui.welcome.carouselContent

@Composable
fun ShelfPalNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: Route = Welcome,
    onOnboardingComplete: () -> Unit = {},
    pendingNotificationTab: BottomNavDestination? = null,
    onPendingNotificationTabConsumed: () -> Unit = {},
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
                    onOnboardingComplete()
                    navHostController.navigate(Home) {
                        popUpTo(Welcome) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Home> {
            BottomNavHost(
                navController = navHostController,
                pendingNotificationTab = pendingNotificationTab,
                onPendingNotificationTabConsumed = onPendingNotificationTabConsumed,
            )
        }

        composable<AddEditItem> { backStackEntry ->
            val addEditItemRoute = backStackEntry.toRoute<AddEditItem>()

            AddEditItemScreen(
                itemId = addEditItemRoute.id,
                navigateBack = { navHostController.popBackStack() },
                navigateToImageCapture = { navHostController.navigate(ImageCapture(it.name)) },
                navController = navHostController
            )
        }

        composable<ImageCapture> {
            val route = it.toRoute<ImageCapture>()
            ImageCaptureScreen(
                navController = navHostController,
                entrySource = CameraEntry.valueOf(route.cameraEntry)
            )
        }

        composable<ItemDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<ItemDetails>()
            ShelfPalItemDetailScreen(
                id = route.id,
                onNavigationIconClick = { navHostController.popBackStack() },
                onActionIconClick = {
                    navHostController.navigate(AddEditItem(route.id))
                },
                showActions = route.showActions
            )
        }

        composable<Route.Settings> {
            SettingsScreen(
                onNavigateBack = { navHostController.popBackStack() },
                launchWebview = {
                    navHostController.navigate(
                        Route.Webview(
                            title = it.title,
                            url = it.url
                        )
                    )
                }
            )
        }

        composable<Route.UpdatesDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.UpdatesDetail>()

            UpdatesDetailScreen(
                params = UpdatesDetailParams(
                    items = route.items,
                    title = route.title,
                    subtitle = route.subtitle,
                    showActions = route.showActions,
                    entrySource = UpdateDetailsEntrySource.valueOf(route.entrySource),
                    updateRecency = route.updateRecency?.let { UpdateRecency.valueOf(it) }
                ),
                onNavigateBack = { navHostController.popBackStack() },
                onItemClick = { id -> navHostController.navigate(ItemDetails(id, showActions = false)) }
            )
        }

        composable<Route.Webview> {
            val route = it.toRoute<Route.Webview>()
            WebviewScreen(
                title = route.title,
                url = route.url,
                onNavigateBack = { navHostController.popBackStack() }
            )
        }
    }
}

@Composable
private fun BottomNavHost(
    navController: NavHostController,
    pendingNotificationTab: BottomNavDestination?,
    onPendingNotificationTabConsumed: () -> Unit,
) {
    val tabNavController = rememberNavController()

    LaunchedEffect(pendingNotificationTab) {
        if (pendingNotificationTab != null) {
            navigateToBottomNav(tabNavController, pendingNotificationTab)
            onPendingNotificationTabConsumed()
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(tabNavController) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = tabNavController,
            startDestination = Home,
        ) {
            composable<Home> {
                HomeScreen(
                    navigateToAddItem = {
                        navController.navigate(AddEditItem())
                    },
                    navigateToItemDetail = { id ->
                        navController.navigate(ItemDetails(id))
                    },
                    navigateToSettings = {
                        navController.navigate(Route.Settings)
                    }
                )
            }

            composable<Route.Notifications> {
                UpdatesScreen(
                    onNavigateToDetailClick = { params ->
                        navController.navigate(
                            Route.UpdatesDetail(
                                items = params.items,
                                title = params.title,
                                subtitle = params.subtitle,
                                showActions = params.showActions,
                                entrySource = params.entrySource.name,
                                updateRecency = params.updateRecency?.name
                            )
                        )
                    },
                    onItemClick = { id ->
                        navController.navigate(ItemDetails(id, showActions = false))
                    }
                )
            }
        }
    }
}
