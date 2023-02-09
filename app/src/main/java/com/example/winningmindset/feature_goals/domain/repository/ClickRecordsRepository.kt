package com.example.winningmindset.feature_goals.domain.repository

import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface ClickRecordsRepository {

    fun getRecordPerGoal(parentId: Int): Flow<List<ClickRecords>>

    suspend fun insertRecord(clickRecord: ClickRecords)

    suspend fun deleteRecord(goal: Goal)
}