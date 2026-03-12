package com.simi.shelfpal.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import shelfpal.composeapp.generated.resources.Res
import shelfpal.composeapp.generated.resources.home
import shelfpal.composeapp.generated.resources.updates


enum class BottomNavDestination(
    val route: Route,
    val title: StringResource,
    val icon: DrawableResource,
    val contentDescription: StringResource
) {
    HOME(
        route = Route.Home,
        title = Res.string.home,
        icon = Res.drawable.home,
        contentDescription = Res.string.home
    ),
    NOTIFICATIONS(
        route = Route.Notifications,
        title = Res.string.updates,
        icon = Res.drawable.updates,
        contentDescription = Res.string.updates
    )
}