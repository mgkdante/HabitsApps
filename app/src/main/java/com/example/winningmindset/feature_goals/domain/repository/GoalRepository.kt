package com.example.winningmindset.feature_goals.domain.repository

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.model.Milestone
import kotlinx.coroutines.flow.Flow

interface GoalRepository {

    fun getGoals(): Flow<List<GoalWithMilestones>>

    suspend fun getGoalById(goalId: Int): GoalWithMilestones?

    suspend fun updateGoal(goal: Goal)

    suspend fun deleteGoal(goal: Goal)

    suspend fun insertMilestones(milestoneList: List<Milestone>)

    suspend fun insertGoalAndMilestones(goal: Goal, milestones: List<Milestone>)


}