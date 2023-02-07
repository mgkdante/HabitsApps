package com.example.winningmindset.feature_goals.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.winningmindset.feature_goals.domain.model.Milestone
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestones(milestoneList: List<Milestone>)

    @Update
    suspend fun updateMilestone(milestone: Milestone)

    @Query("DELETE FROM milestone WHERE parentGoal = :parentGoal")
    suspend fun deleteMilestone(parentGoal: String)

}