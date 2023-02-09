package com.example.winningmindset.feature_goals.presentation.goals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winningmindset.feature_goals.domain.model.ClickRecords
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.feature_goals.domain.use_case.GoalUseCases
import com.example.winningmindset.feature_goals.domain.util.GoalOrder
import com.example.winningmindset.feature_goals.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Days
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val goalUseCases: GoalUseCases
) : ViewModel() {

    private val _state = mutableStateOf(GoalsState())
    val state: State<GoalsState> = _state

    private var recentDeletedGoal: Goal? = null

    private var recentlyDeletedMilestones: List<Milestone> = emptyList()

    private var getGoalsJob: Job? = null

    init {
        getGoals(GoalOrder.Date(OrderType.Descending))

        state.value.goalsWithMilestones.forEach {
            if (dateDiff(it.goal) == 1) {
                onEvent(GoalsEvent.SetButtonToFalse(it.goal))
            }
        }

    }

    fun onEvent(event: GoalsEvent) {
        when (event) {
            is GoalsEvent.Order -> {
                if (state.value.goalOrder::class == event.goalOrder::class
                    && state.value.goalOrder.orderType == event.goalOrder.orderType
                ) {
                    return
                }
                getGoals(event.goalOrder)
            }

            is GoalsEvent.DeleteGoal -> {
                viewModelScope.launch {
                    goalUseCases.deleteGoal(event.goal)
                    recentDeletedGoal = event.goal
                    recentlyDeletedMilestones = event.milestone
                }
            }

            is GoalsEvent.RestoreGoal -> {
                viewModelScope.launch {
                    goalUseCases.addGoalWithMilestones(
                        recentDeletedGoal ?: return@launch,
                        recentlyDeletedMilestones
                    )
                }
            }

            is GoalsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is GoalsEvent.ActionClick -> {
                viewModelScope.launch {
                    goalUseCases.updateGoal(
                        event.goal.copy(
                            isClicked = !event.goal.isClicked,
                            lastClick = System.currentTimeMillis(),
                            totalDays = event.goal.totalDays.plus(1)
                        )
                    )
                    if (!event.goal.isClicked) {
                        event.goal.goalId?.let {
                            ClickRecords(
                                parentId = it,
                                currentDay = System.currentTimeMillis(),
                                recordId = null
                            )
                        }?.let {
                            goalUseCases.insertRecord(
                                it
                            )
                        }
                    } else if (event.goal.isClicked) {
                        goalUseCases.deleteRecord(
                            event.goal
                        )
                        goalUseCases.updateGoal(
                            event.goal.copy(
                                totalDays = event.goal.totalDays.minus(1),
                                isClicked = false
                            )
                        )
                    }
                }
            }

            is GoalsEvent.SetButtonToFalse -> {
                viewModelScope.launch {
                    goalUseCases.updateGoal(
                        event.goal.copy(
                            isClicked = false
                        )
                    )
                }
            }
        }
    }


    private fun getGoals(goalOrder: GoalOrder) {
        getGoalsJob?.cancel()
        getGoalsJob = goalUseCases.getGoalsWithMilestones(goalOrder)
            .onEach { goals ->
                _state.value = state.value.copy(
                    goalsWithMilestones = goals,
                    goalOrder = goalOrder
                )
            }.launchIn(viewModelScope)
    }


    private fun dateDiff(goal: Goal): Int {
        return Days.daysBetween(
            DateTime(goal.lastClick).withTimeAtStartOfDay().toLocalDate(),
            DateTime(System.currentTimeMillis()).toLocalDate()
        ).days
    }

}