package com.example.winningmindset.feature_goals.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import com.example.winningmindset.feature_goals.domain.model.Milestone
import kotlinx.coroutines.flow.Flow


@Dao
interface GoalDao {

    @Transaction
    @Query("SELECT * FROM goal")
    fun getGoals(): Flow<List<GoalWithMilestones>>

    @Query("SELECT * FROM goal WHERE goalId = :goalId")
    suspend fun getGoalById(goalId: Int): GoalWithMilestones?

    @Update
    suspend fun updateGoal(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal): Long

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestones(milestoneList: List<Milestone>)

    @Transaction
    suspend fun insertGoalAndMilestones(goal: Goal, milestones: List<Milestone>) {
        val goalId = insertGoal(goal)
        for (milestone in milestones) {
            milestone.parentId = goalId.toInt()
            insertMilestones(listOf(milestone))
        }
    }
}