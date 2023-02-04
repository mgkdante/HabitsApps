package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import com.example.winningmindset.feature_goals.domain.model.Milestone

data class MilestoneState(
    val milestoneId: Int = 0,
    val parentGoal: String = "",
    val milestone: String = "",
    val dateCreated: Long = 0,
    val totalDay: Int = 0,
    val currentDay: Long = 0
)

fun MilestoneState.toMilestone(): Milestone = Milestone(
    milestoneId = milestoneId,
    parentGoal = parentGoal,
    milestone = milestone,
    dateCreated = dateCreated
)