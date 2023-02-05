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
    val isClicked: Boolean = false,
    val totalDays: Int = 0,
    val lastClick: Long? = null
) {
    companion object GoalColor {
        val colors = listOf(
/*                        Color(0xFFEF9A9A),
                        Color(0xFFF48FB1),
                        Color(0xFF80CBC4),
                        Color(0xFFA5D6A7),
                        Color(0xFFFFCC80),
                        Color(0xFFFFAB91),
                        Color(0xFF81D4FA),
                        Color(0xFFCE93D8),
                        Color(0xFFB39DDB),*/
            Color(0xFFEE6055),
            Color(0xFF60d394),
            Color(0xFFAAF683),
            Color(0xFFF8C64D),
            Color(0xFFFF9770),
            Color(0xFF70D6FF),
            Color(0xFFFF70A6),
            Color(0xFFFFCC80),
            Color(0xFF9d71e8),
            Color(0xFF973D3D),
            Color(0xFFffa69e),
            Color(0xFF5aa9e6),

        ).sortedBy { color -> color.value }
    }
}

class InvalidGoalException(message: String) : Exception(message)