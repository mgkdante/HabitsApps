package com.example.winningmindset.feature_goals.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone

@Database(
    entities = [Goal::class, Milestone::class, ClickRecords::class],
    version = 1,
    exportSchema = false
)
abstract class GoalDatabase: RoomDatabase() {
    abstract val goalDao: GoalDao
    abstract val milestoneDao: MilestoneDao
    abstract val clickRecordsDao: ClickRecordsDao

    companion object {
        const val DATABASE_NAME = "goals_db"
    }
}