package com.example.winningmindset.feature_goals.data.repository

import com.example.winningmindset.feature_goals.data.data_source.MilestoneDao
import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.feature_goals.domain.repository.MilestoneRepository

class MilestoneRepositoryImpl(
    private val dao: MilestoneDao
) : MilestoneRepository {

    override suspend fun insertMilestones(milestoneList: List<Milestone>) {
        return dao.insertMilestones(milestoneList)
    }

    override suspend fun updateMilestone(milestone: Milestone) {
        return dao.updateMilestone(milestone)
    }

    override suspend fun deleteMilestone(milestone: Milestone) {
        return dao.deleteMilestone(milestone)
    }
}