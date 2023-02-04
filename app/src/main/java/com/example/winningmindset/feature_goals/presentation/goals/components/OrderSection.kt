package com.example.winningmindset.feature_goals.presentation.goals.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.winningmindset.feature_goals.domain.util.GoalOrder
import com.example.winningmindset.feature_goals.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    goalOrder: GoalOrder = GoalOrder.Date(OrderType.Descending),
    onOrderChange: (GoalOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Goal Title",
                selected = goalOrder is GoalOrder.Goal,
                onSelect = { onOrderChange(GoalOrder.Goal(goalOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Goal Date",
                selected = goalOrder is GoalOrder.Date,
                onSelect = { onOrderChange(GoalOrder.Date(goalOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = goalOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(goalOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = goalOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(goalOrder.copy(OrderType.Descending)) }
            )
        }
    }
}