package com.example.winningmindset.feature_goals.presentation.util

sealed class Screen(
    val route: String
){
    object GoalsScreen: Screen("goals_screen")

    object AddEditScreen: Screen("add_edit_screen")
}
