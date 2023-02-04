package com.example.winningmindset.feature_goals.data.repository

import com.example.winningmindset.feature_goals.data.data_source.GoalDao
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow

class GoalRepositoryImpl(
    private val dao: GoalDao
) : GoalRepository {
    override fun getGoals(): Flow<List<GoalWithMilestones>> {
        return dao.getGoals()
    }

    override suspend fun getGoalById(goalId: Int): GoalWithMilestones? {
        return dao.getGoalById(goalId)
    }

    override suspend fun insertGoal(goal: Goal) {
        return dao.insertGoal(goal)
    }

    override suspend fun deleteGoal(goal: Goal) {
        return dao.deleteGoal(goal)
    }

}