package com.example.winningmindset.feature_goals.presentation.goals

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.feature_goals.domain.util.GoalOrder

sealed class GoalsEvent {
    data class Order(val goalOrder: GoalOrder): GoalsEvent()
    data class DeleteGoal(val goal: Goal, val milestone: List<Milestone>): GoalsEvent()
    data class ActionClick(val goal: Goal): GoalsEvent()
    data class SetButtonToFalse(val goal: Goal): GoalsEvent()

    object RestoreGoal: GoalsEvent()
    object ToggleOrderSection: GoalsEvent()
}
