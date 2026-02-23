package com.simi.shelfpal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.simi.shelfpal.navigation.BottomNavDestination
import com.simi.shelfpal.navigation.ShelfPalNavHost
import com.simi.shelfpal.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val startDestination = BottomNavDestination.HOME
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val shouldShowBottomBar = BottomNavDestination.entries.any {
            currentDestination?.hasRoute(it.route::class) == true
        }

        InitializeNotificationListener(navController)

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    shouldShowBottomBar = shouldShowBottomBar,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        ) {
            ShelfPalNavHost(
                navHostController = navController,
                startDestination = startDestination.route,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            )
        }
    }
}

@Composable
private fun BottomNavBar(
    shouldShowBottomBar: Boolean,
    currentDestination: NavDestination?,
    navController: NavController
) {
    AnimatedVisibility(
        visible = shouldShowBottomBar,
        enter = slideInVertically(initialOffsetY = {it}),
        exit = slideOutVertically(targetOffsetY = {it})
    ) {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
            BottomNavDestination.entries.forEachIndexed { index, destination ->
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(destination.route::class) == true,
                    onClick = {
                        navigateToBottomNav(
                            navController = navController,
                            destination = destination
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = stringResource(destination.contentDescription)
                        )
                    },
                    label = { Text(stringResource(destination.title)) }
                )
            }
        }
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

private fun navigateToBottomNav(
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