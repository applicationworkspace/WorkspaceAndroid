package com.workspaceandroid.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.workspaceandroid.navigation.BottomBarScreen
import com.workspaceandroid.ui.screens.collection.CollectionScreen
import com.workspaceandroid.ui.screens.home.HomeScreen
import com.workspaceandroid.ui.screens.settings.SettingsScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN_ROUTE,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        collectionNavGraph(navController = navController)
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }
        authNavGraph(navController = navController)
    }
}