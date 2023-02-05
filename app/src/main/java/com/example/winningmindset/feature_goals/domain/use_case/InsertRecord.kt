package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.repository.ClickRecordsRepository

class InsertRecord(
    private val repository: ClickRecordsRepository
) {
    suspend operator fun invoke(clickRecord: ClickRecords){
        repository.insertRecord(clickRecord)
    }
}