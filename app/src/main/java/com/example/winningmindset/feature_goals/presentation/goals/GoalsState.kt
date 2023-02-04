package com.example.winningmindset.feature_goals.presentation.goals

import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.util.GoalOrder
import com.example.winningmindset.feature_goals.domain.util.OrderType

data class GoalsState(
    val goalsWithMilestones: List<GoalWithMilestones> = emptyList(),
    val goalOrder: GoalOrder = GoalOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
