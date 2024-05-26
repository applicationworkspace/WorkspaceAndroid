package com.workspaceandroid.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.workspaceandroid.MainScreen
import com.workspaceandroid.navigation.navGraph.Graph.ROOT_ROUTE

@Composable
fun RootNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION_ROUTE,
        route = ROOT_ROUTE
    ) {

        authNavGraph(navController = navController)
        composable(route = Graph.MAIN_ROUTE) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT_ROUTE = "root"
    const val MAIN_ROUTE = "main"
    const val AUTHENTICATION_ROUTE = "authentication"
    const val GROUPS_ROUTE = "groups"
}