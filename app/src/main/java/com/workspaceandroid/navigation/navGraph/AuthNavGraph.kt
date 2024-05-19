package com.workspaceandroid.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.workspaceandroid.navigation.Screen
import com.workspaceandroid.navigation.navGraph.Graph.AUTHENTICATION_ROUTE
import com.workspaceandroid.ui.screens.login.LoginScreen
import com.workspaceandroid.ui.screens.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTHENTICATION_ROUTE
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}