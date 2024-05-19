package com.workspaceandroid.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.workspaceandroid.navigation.Screen
import com.workspaceandroid.navigation.navGraph.Graph.COLLECTION_ROUTE
import com.workspaceandroid.ui.screens.collection.CollectionScreen
import com.workspaceandroid.ui.screens.add_phrase.AddPhraseScreen

fun NavGraphBuilder.collectionNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Collection.route,
        route = COLLECTION_ROUTE
    ) {
        composable(route = Screen.Collection.route) {
            CollectionScreen(navController = navController)
        }
        composable(route = Screen.AddPhrase.route) {
            AddPhraseScreen(navController = navController)
        }
    }
}