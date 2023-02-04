package com.example.winningmindset.feature_goals.domain.use_case

import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.InvalidGoalException
import com.example.winningmindset.feature_goals.domain.repository.GoalRepository

class AddGoal(
    private val repository: GoalRepository
) {
    @Throws(InvalidGoalException::class)
    suspend operator fun invoke(goal: Goal){
        if (goal.goal.isBlank()){
            throw InvalidGoalException("The goal title cannot be empty")
        }
        if (goal.typeOfMindset.isBlank()){
            throw InvalidGoalException("The goal title cannot be empty")
        }
        repository.insertGoal(goal)
    }
}