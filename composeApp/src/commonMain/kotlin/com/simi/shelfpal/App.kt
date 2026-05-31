package com.simi.shelfpal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        var pendingNotificationTab by remember { mutableStateOf<BottomNavDestination?>(null) }

        InitializeNotificationListener {
            navigateToBottomNav(
                navController,
                BottomNavDestination.HOME
            )
            pendingNotificationTab = BottomNavDestination.NOTIFICATIONS
        }

        ShelfPalNavHost(
            navHostController = navController,
            startDestination = startDestination,
            onOnboardingComplete = { settings.saveOnboardingCompleted() },
            pendingNotificationTab = pendingNotificationTab,
            onPendingNotificationTabConsumed = { pendingNotificationTab = null }
        )
    }
}

@Composable
private fun InitializeNotificationListener(
    onNavigateToNotifications: () -> Unit,
) {
    LaunchedEffect(Unit) {
        NotifierManager.getLocalNotifier().removeAll()

        NotifierManager.addListener(object: NotifierManager.Listener {
            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                onNavigateToNotifications()
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