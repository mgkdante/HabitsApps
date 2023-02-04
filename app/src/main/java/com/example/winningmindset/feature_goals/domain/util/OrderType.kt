package com.example.winningmindset.feature_goals.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}