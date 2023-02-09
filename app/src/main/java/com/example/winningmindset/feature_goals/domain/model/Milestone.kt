package com.example.winningmindset.feature_goals.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "milestone",
    foreignKeys = [ForeignKey(
        entity = Goal::class,
        parentColumns = ["goalId"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    ],
    indices = [Index(value = ["milestoneId"], unique = true)],
)
data class Milestone(
    @PrimaryKey(autoGenerate = true)
    val milestoneId: Int,
    var parentId: Int,
    val milestone: String,
    val dateCreated: Long
)