package com.example.winningmindset.feature_goals.data.repository

import com.example.winningmindset.feature_goals.data.data_source.ClickRecordsDao
import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.repository.ClickRecordsRepository
import kotlinx.coroutines.flow.Flow

class ClickRecordRepositoryImpl(
    private val dao: ClickRecordsDao
) : ClickRecordsRepository {

    override fun getRecordPerGoal(parentGoal: String): Flow<List<ClickRecords>> {
        return dao.getRecordsPerGoal(parentGoal)
    }

    override suspend fun insertRecord(clickRecord: ClickRecords) {
        dao.insertRecord(clickRecord)
    }

    override suspend fun deleteRecord(goal: Goal) {
        dao.deleteRecord(goal.goal)
    }

}