package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import com.example.winningmindset.feature_goals.domain.model.Goal

data class GoalState(
    var goalId: Int = 0,
    val goal: String = "",
    val typeOfMindset: String = "",
    val color: Long = 0,
    val isClicked: Boolean = false,
    val totalDays: Int = 0,
    val dateCreated: Long = System.currentTimeMillis(),
    val lastClick: Long = 0
)


fun GoalState.toGoal(): Goal = Goal(
    goalId,
    goal,
    typeOfMindset,
    color,
    dateCreated,
    isClicked,
    totalDays,
    lastClick

)