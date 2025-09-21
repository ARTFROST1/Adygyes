package com.adygyes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adygyes.presentation.ui.screens.map.MapScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Map.route
    ) {
        composable(Destinations.Map.route) {
            MapScreen(
                onNavigateToAttraction = { attractionId ->
                    navController.navigate(Destinations.AttractionDetail.createRoute(attractionId))
                },
                onNavigateToSearch = {
                    navController.navigate(Destinations.Search.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Destinations.Settings.route)
                }
            )
        }
        
        composable(
            route = Destinations.AttractionDetail.route,
            arguments = listOf(
                navArgument("attractionId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val attractionId = backStackEntry.arguments?.getLong("attractionId") ?: 0L
            // TODO: Implement AttractionDetailScreen
            // AttractionDetailScreen(
            //     attractionId = attractionId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }
        
        composable(Destinations.Search.route) {
            // TODO: Implement SearchScreen
            // SearchScreen(
            //     onNavigateBack = { navController.popBackStack() },
            //     onNavigateToAttraction = { attractionId ->
            //         navController.navigate(Destinations.AttractionDetail.createRoute(attractionId))
            //     }
            // )
        }
        
        composable(Destinations.Settings.route) {
            // TODO: Implement SettingsScreen
            // SettingsScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }
    }
}
