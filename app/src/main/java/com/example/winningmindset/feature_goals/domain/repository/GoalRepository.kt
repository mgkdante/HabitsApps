package com.example.winningmindset.feature_goals.domain.repository

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import kotlinx.coroutines.flow.Flow

interface GoalRepository {

    fun getGoals(): Flow<List<GoalWithMilestones>>

    suspend fun getGoalById(goalId: Int): GoalWithMilestones?

    suspend fun insertGoal(goal: Goal)

    suspend fun deleteGoal(goal: Goal)

}