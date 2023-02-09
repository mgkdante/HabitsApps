package com.example.winningmindset.feature_goals.presentation.goals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.feature_goals.domain.use_case.GoalUseCases
import com.example.winningmindset.feature_goals.domain.util.GoalOrder
import com.example.winningmindset.feature_goals.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job

import kotlinx.coroutines.flow.first
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

        updateButtons()
    }

    fun updateButtons() {
        viewModelScope.launch {
            val goalsWithMilestones = goalUseCases.getGoalsWithMilestones().first()
            val newState = state.value.copy()
            goalsWithMilestones.forEach {
                if (dateDiff(it.goal) == 1) {
                    onEvent(GoalsEvent.SetButtonToFalse(it.goal))
                }
            }
            _state.value = newState
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
                    val updatedGoal = event.goal.copy(
                        isClicked = !event.goal.isClicked,
                        lastClick = if (!event.goal.isClicked) System.currentTimeMillis() else event.goal.lastClick,
                        totalDays = event.goal.totalDays.plus(if (event.goal.isClicked) -1 else 1),
                        streakInDays = calculateStreak(event.goal.lastClick, event.goal.isClicked, event.goal.streakInDays)
                    )
                    goalUseCases.updateGoal(updatedGoal)
                }
            }

            is GoalsEvent.SetButtonToFalse -> {
                viewModelScope.launch {
                    goalUseCases.updateGoal(
                        event.goal.copy(
                            isClicked = false
                        )
                    )
                    // Update the state with the new goal
                    _state.value =
                        _state.value.copy(goalsWithMilestones = state.value.goalsWithMilestones.map {
                            if (it.goal.goalId == event.goal.goalId) {
                                it.copy(goal = event.goal)
                            } else {
                                it
                            }
                        })
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


    private fun calculateStreak(lastClick: Long?, isClicked: Boolean, streak: Int): Int {
        return if (lastClick != null) {
            val daysBetween = Days.daysBetween(
                DateTime(lastClick).withTimeAtStartOfDay().toLocalDate(),
                DateTime(System.currentTimeMillis()).toLocalDate()
            ).days
            if (!isClicked && daysBetween <= 1) {
                streak + 1
            } else if (isClicked && daysBetween <= 1){
                streak - 1
            } else  1
        } else {
            0
        }
    }

    private fun dateDiff(goal: Goal): Int {
        val lastClickDate = DateTime(goal.lastClick).withTimeAtStartOfDay().toLocalDate()
        val currentDate = DateTime(System.currentTimeMillis()).toLocalDate()
        return Days.daysBetween(lastClickDate, currentDate).days
    }
}