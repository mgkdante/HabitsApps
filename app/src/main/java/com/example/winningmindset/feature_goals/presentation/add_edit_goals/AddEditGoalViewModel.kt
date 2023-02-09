package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winningmindset.feature_goals.domain.model.InvalidGoalException
import com.example.winningmindset.feature_goals.domain.use_case.GoalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditGoalViewModel @Inject constructor(
    private val goalUseCases: GoalUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _goal = mutableStateOf(
        GoalState()
    )
    val goal: State<GoalState> = _goal


    private val _singleMilestoneState = mutableStateOf(MilestoneState())
    val singleMilestoneState = _singleMilestoneState

    var milestonesListState = mutableStateListOf<MilestoneState>()
        private set


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentGoalId: Int? = null
        private set


    init {
        savedStateHandle.get<Int>("goalId")?.let { goalId ->
            if (goalId != -1) {
                currentGoalId = goalId
                viewModelScope.launch {
                    goalUseCases.getGoalWithMilestones(goalId)?.also { goalWithMilestones ->
                        currentGoalId = goalWithMilestones.goal.goalId
                        _goal.value = goal.value.copy(
                            goal = goalWithMilestones.goal.goal,
                            typeOfMindset = goalWithMilestones.goal.typeOfMindset,
                            color = goalWithMilestones.goal.color,
                            lastClick = goalWithMilestones.goal.lastClick,
                            dateCreated = goalWithMilestones.goal.dateCreated,
                            totalDays = goalWithMilestones.goal.totalDays,
                            isClicked = goalWithMilestones.goal.isClicked,
                            goalId = currentGoalId
                        )
                        goalWithMilestones.milestones.forEach { milestone ->
                            milestonesListState.add(
                                singleMilestoneState.value.copy(
                                    milestone = milestone.milestone,
                                    milestoneId = milestone.milestoneId,
                                    dateCreated = milestone.dateCreated
                                )
                            )
                        }

                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditGoalEvent) {
        when (event) {
            is AddEditGoalEvent.EnterGoal -> {
                _goal.value = goal.value.copy(
                    goal = event.value
                )
            }

            is AddEditGoalEvent.EnterTypeOfMindset -> {
                _goal.value = goal.value.copy(
                    typeOfMindset = event.value
                )
            }

            is AddEditGoalEvent.SaveMilestoneToList -> {
                milestonesListState.add(_singleMilestoneState.value)
                _singleMilestoneState.value = singleMilestoneState.value.copy(
                    milestone = ""
                )
            }

            is AddEditGoalEvent.EnterMilestone -> {
                _singleMilestoneState.value = singleMilestoneState.value.copy(
                    milestone = event.milestone,
                    dateCreated = System.currentTimeMillis()
                )
            }

            is AddEditGoalEvent.DeleteMilestone -> {
                milestonesListState.remove(event.milestone)
            }

            is AddEditGoalEvent.OnChangeColor -> {
                _goal.value = goal.value.copy(
                    color = event.color
                )
            }

            is AddEditGoalEvent.SaveGoal -> {
                viewModelScope.launch {
                    try {
                        goalUseCases.addGoalWithMilestones(
                            goal.value.copy(
                                goal = goal.value.goal,
                                typeOfMindset = goal.value.typeOfMindset,
                                color = goal.value.color,
                                isClicked = goal.value.isClicked,
                                totalDays = goal.value.totalDays,
                                dateCreated = goal.value.dateCreated,
                                lastClick = goal.value.lastClick,
                                goalId = currentGoalId
                            ).toGoal(),
                            milestonesListState.map {
                                it.toMilestone()
                            }
                        )
                        _eventFlow.emit(UiEvent.SaveGoal)
                    } catch (e: InvalidGoalException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveGoal : UiEvent()

    }
}