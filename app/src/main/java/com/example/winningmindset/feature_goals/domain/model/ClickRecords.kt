package com.example.winningmindset.feature_goals.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "click_records",
    foreignKeys = [ForeignKey(
        entity = Goal::class,
        parentColumns = ["goalId"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    ],
    indices = [Index(value = ["recordId"], unique = true)]
)
data class ClickRecords(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int? = null,
    val parentId: Int,
    val currentDay: Long,
)