package com.example.winningmindset.feature_goals.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity(tableName = "goal", indices = [Index(value = ["goal"], unique = true)])
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Int? = null,
    val goal: String,
    val typeOfMindset: String,
    val color: Long,
    val dateCreated: Long,
    val isClicked: Boolean,
    val totalDays: Int?,
    val currentDay: Long?
){
    companion object GoalColor {
        val colors = listOf(
            Color(0xFFEF9A9A),
            Color(0xFFF48FB1),
            Color(0xFF80CBC4),
            Color(0xFFA5D6A7),
            Color(0xFFFFCC80),
            Color(0xFFFFAB91),
            Color(0xFF81D4FA),
            Color(0xFFCE93D8),
            Color(0xFFB39DDB)
        )
    }
}

class InvalidGoalException(message: String): Exception(message)