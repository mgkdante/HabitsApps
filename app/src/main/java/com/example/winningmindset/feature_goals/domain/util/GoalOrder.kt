package com.example.winningmindset.feature_goals.domain.util

sealed class GoalOrder(
    val orderType: OrderType
) {
    class Goal(orderType: OrderType): GoalOrder(orderType)
    class Date(orderType: OrderType): GoalOrder(orderType)

    fun copy(orderType: OrderType): GoalOrder{
        return when(this){
            is Goal -> Goal(orderType)
            is Date -> Date(orderType)
        }
    }
}