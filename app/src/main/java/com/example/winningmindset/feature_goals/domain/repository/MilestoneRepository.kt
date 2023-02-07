package com.example.winningmindset.feature_goals.domain.repository


import com.example.winningmindset.feature_goals.domain.model.Milestone

interface MilestoneRepository {

    suspend fun insertMilestones(milestoneList: List<Milestone>)

    suspend fun updateMilestone(milestone: Milestone)

    suspend fun deleteMilestone(parentGoal: String)
}