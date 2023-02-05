package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.repository.ClickRecordsRepository
import kotlinx.coroutines.flow.Flow

class GetRecordsPerGoal(
    private val repository: ClickRecordsRepository
) {
    operator fun invoke(parentGoal: String): Flow<List<ClickRecords>> {
       return repository.getRecordPerGoal(parentGoal)
    }
}