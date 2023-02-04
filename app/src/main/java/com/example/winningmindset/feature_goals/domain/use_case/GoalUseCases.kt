package com.example.winningmindset.feature_goals.domain.use_case

data class GoalUseCases(
    val getGoalsWithMilestones: GetGoalsWithMilestones,
    val getGoalWithMilestones: GetGoalWithMileStones,
    val addGoal: AddGoal,
    val deleteGoal: DeleteGoal,
    val addMilestoneList: AddMilestoneList
)
