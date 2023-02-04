package com.example.winningmindset.feature_goals.presentation.util

sealed class Screen(
    val route: String,
    val title: String
){
    object GoalsScreen: Screen("goals_screen", "Goals")

    object AddEditScreen: Screen("add_edit_screen", "Add or edit item")
}
