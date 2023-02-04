package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.repository.GoalRepository

class GetGoalWithMileStones(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goalId: Int): GoalWithMilestones?{
        return repository.getGoalById(goalId)
    }
}