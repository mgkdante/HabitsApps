package com.example.winningmindset.feature_goals.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.winningmindset.feature_goals.presentation.add_edit_goals.AddEditGoalScreen
import com.example.winningmindset.feature_goals.presentation.goals.GoalsScreen
import com.example.winningmindset.feature_goals.presentation.util.Screen
import com.example.winningmindset.ui.theme.WinningMindsetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WinningMindsetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.GoalsScreen.route
                    ){
                        composable(route = Screen.GoalsScreen.route){
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
                            AddEditGoalScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WinningMindsetTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.GoalsScreen.route
        ) {
            composable(route = Screen.GoalsScreen.route) {
                GoalsScreen(navController = navController)
            }
        }
    }
}