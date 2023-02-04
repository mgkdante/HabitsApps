package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.repository.GoalRepository
import com.example.winningmindset.feature_goals.domain.util.GoalOrder
import com.example.winningmindset.feature_goals.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGoalsWithMilestones(
    private val repository: GoalRepository
) {
    operator fun invoke(
        goalOrder: GoalOrder = GoalOrder.Date(OrderType.Descending)
    ): Flow<List<GoalWithMilestones>> {
        return repository.getGoals().map { goals ->
            when(goalOrder.orderType){
                is OrderType.Ascending -> {
                    when(goalOrder) {
                        is GoalOrder.Goal -> goals.sortedBy { it.goal.goal.lowercase() }
                        is GoalOrder.Date -> goals.sortedBy { it.goal.dateCreated }
                    }
                }
                is OrderType.Descending -> {
                    when(goalOrder) {
                        is GoalOrder.Goal -> goals.sortedBy { it.goal.goal.lowercase() }
                        is GoalOrder.Date -> goals.sortedBy { it.goal.dateCreated }
                    }
                }
            }
        }
    }
}