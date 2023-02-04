package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.feature_goals.domain.repository.MilestoneRepository

class AddMilestoneList(
    private val repository: MilestoneRepository
) {
    suspend operator fun invoke(milestones: List<Milestone>){
        repository.insertMilestones(milestones)
    }
}