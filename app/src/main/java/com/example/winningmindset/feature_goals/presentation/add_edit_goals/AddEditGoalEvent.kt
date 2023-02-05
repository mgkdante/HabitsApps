package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone

sealed class AddEditGoalEvent {
    data class EnterGoal(val value: String): AddEditGoalEvent()

    data class EnterTypeOfMindset(val value: String): AddEditGoalEvent()

    data class EnterMilestone(val milestone: String) : AddEditGoalEvent()

    data class SaveMilestoneToList(val milestone: Milestone): AddEditGoalEvent()

    data class DeleteMilestone(val milestone: MilestoneState): AddEditGoalEvent()

    data class OnChangeColor(val color: Long): AddEditGoalEvent()

    data class UpdateGoal(val goal: Goal): AddEditGoalEvent()

    object SaveGoal: AddEditGoalEvent()
}
