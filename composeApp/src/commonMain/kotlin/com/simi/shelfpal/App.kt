package com.simi.shelfpal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.simi.shelfpal.data.settings.ShelfPalSettings
import com.simi.shelfpal.navigation.BottomNavDestination
import com.simi.shelfpal.navigation.Route
import com.simi.shelfpal.navigation.ShelfPalNavHost
import com.simi.shelfpal.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val settings = remember { ShelfPalSettings() }
        val startDestination = remember {
            if (settings.hasCompletedOnboarding()) Route.Home else Route.Welcome
        }

        InitializeNotificationListener(navController)
        ShelfPalNavHost(
            navHostController = navController,
            startDestination = startDestination,
            onOnboardingComplete = { settings.saveOnboardingCompleted() }
        )
    }
}

@Composable
private fun InitializeNotificationListener(
    navController: NavController
) {
    LaunchedEffect(Unit) {
        NotifierManager.getLocalNotifier().removeAll()

        NotifierManager.addListener(object: NotifierManager.Listener {
            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                navigateToBottomNav(
                    navController = navController,
                    destination = BottomNavDestination.NOTIFICATIONS
                )
            }
        })
    }
}

fun navigateToBottomNav(
    navController: NavController,
    destination: BottomNavDestination
) {
    navController.navigate(destination.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}