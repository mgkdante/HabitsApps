package com.example.winningmindset.feature_goals.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import kotlinx.coroutines.flow.Flow

@Dao
interface ClickRecordsDao {

    @Query("SELECT * FROM click_records WHERE parentId = :parentId")
    fun getRecordsPerGoal(parentId: Int): Flow<List<ClickRecords>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: ClickRecords)

    @Query("DELETE FROM click_records WHERE recordId IN (" +
            "SELECT MAX(recordId) AS recordId FROM click_records WHERE parentId = :parentId GROUP BY parentId)")
    suspend fun deleteRecord(parentId: Int)

}