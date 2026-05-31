package com.simi.shelfpal.ui.home.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.simi.shelfpal.navigateToBottomNav
import com.simi.shelfpal.navigation.BottomNavDestination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        BottomNavDestination.entries.forEach { destination ->
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
