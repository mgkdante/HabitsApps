package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import androidx.compose.ui.graphics.toArgb
import com.example.winningmindset.feature_goals.domain.model.Goal

data class GoalState(
    val goal: String = "",
    val typeOfMindset: String = "",
    val color: Long = Goal.colors.random().toArgb().toLong(),
    val isClicked: Boolean = false,
    val lastClick: Long = 0,
    val totalDays: Int = 0,
    val dateCreated:Long = System.currentTimeMillis(),
    val goalId: Int? = null
)


fun GoalState.toGoal(): Goal = Goal(
    goal = goal,
    typeOfMindset = typeOfMindset,
    color = color,
    isClicked = isClicked,
    lastClick = lastClick,
    totalDays = totalDays,
    dateCreated = dateCreated,
    goalId = goalId
)
