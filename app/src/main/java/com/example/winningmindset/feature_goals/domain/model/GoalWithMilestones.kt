package com.example.winningmindset.feature_goals.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class GoalWithMilestones(
    @Embedded val goal: Goal,
    @Relation(
        parentColumn = "goalId",
        entityColumn = "parentId"
    )
    val milestones: List<Milestone>
)
