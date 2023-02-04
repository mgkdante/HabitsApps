package com.example.winningmindset.feature_goals.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.GoalWithMilestones
import kotlinx.coroutines.flow.Flow


@Dao
interface GoalDao {

    @Transaction
    @Query("SELECT * FROM goal")
    fun getGoals(): Flow<List<GoalWithMilestones>>

    @Query("SELECT * FROM goal WHERE goalId = :goalId")
    suspend fun getGoalById(goalId: Int): GoalWithMilestones?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)
}