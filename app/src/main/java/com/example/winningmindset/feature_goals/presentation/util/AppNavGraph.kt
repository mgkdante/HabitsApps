package com.example.winningmindset.feature_goals.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.winningmindset.feature_goals.presentation.add_edit_goals.AddEditGoalScreen
import com.example.winningmindset.feature_goals.presentation.goals.GoalsScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GoalsScreen.route
    ) {
        composable(route = Screen.GoalsScreen.route) {
            GoalsScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditScreen.route +
                    "?goalId={goalId}",
            arguments = listOf(
                navArgument(
                    name = "goalId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditGoalScreen(navController = navController,
                onNavigateUp = { navController.navigateUp() } )
        }
    }
}