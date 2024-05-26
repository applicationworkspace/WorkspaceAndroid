package com.workspaceandroid.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.workspaceandroid.navigation.BottomBarScreen
import com.workspaceandroid.navigation.Screen
import com.workspaceandroid.ui.feature.add_group.AddGroupScreen
import com.workspaceandroid.ui.feature.add_phrase.AddPhraseScreen
import com.workspaceandroid.ui.feature.collection.CollectionScreen
import com.workspaceandroid.ui.feature.groups.GroupsScreen
import com.workspaceandroid.ui.feature.home.HomeScreen
import com.workspaceandroid.ui.feature.login.LoginScreen
import com.workspaceandroid.ui.feature.settings.SettingsScreen
import com.workspaceandroid.ui.feature.signup.SignUpScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.MAIN_ROUTE,
        startDestination = BottomBarScreen.Collection.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(route = BottomBarScreen.Collection.route) {
            CollectionScreen(navController = navController)
        }

        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }


        composable(route = Screen.AddPhrase.route) {
            AddPhraseScreen(navController = navController)
        }
        authNavGraph(navController = navController)
        groupsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.groupsNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Groups.route,
        route = Graph.GROUPS_ROUTE
    ) {

        composable(route = Screen.Groups.route) {
            GroupsScreen(navController = navController)
        }

        composable(route = Screen.AddGroup.route) {
            AddGroupScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTHENTICATION_ROUTE
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}