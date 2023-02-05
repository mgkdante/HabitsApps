package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.repository.ClickRecordsRepository

class DeleteRecord(
    private val repository: ClickRecordsRepository
) {
    suspend operator fun invoke(goal: Goal){
        repository.deleteRecord(goal)
    }
}