package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winningmindset.feature_goals.domain.model.Goal
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

    private val _goalTitle = mutableStateOf(
        GoalTextFieldState(
            hint = "Enter Goal Title"
        )
    )
    val goalTitle: State<GoalTextFieldState> = _goalTitle


    private val _typeOfMindset = mutableStateOf(
        GoalTextFieldState(
            hint = "Enter the mindset needed for this goal"
        )
    )
    val typeOfMindset: State<GoalTextFieldState> = _typeOfMindset

    private val _singleMilestoneState = mutableStateOf(MilestoneState())
    val singleMilestoneState = _singleMilestoneState

    var milestonesListState = mutableStateListOf<MilestoneState>()
        private set

    private val _goalColor = mutableStateOf(GoalColorState(Goal.colors[0].toArgb().toLong()))
    val goalColor: State<GoalColorState> = _goalColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentGoalId: Int? = null


    init {
        savedStateHandle.get<Int>("goalId")?.let { goalId ->
            if (goalId != -1) {
                viewModelScope.launch {
                    goalUseCases.getGoalWithMilestones(goalId)?.also { goalWithMilestones ->
                        currentGoalId = goalWithMilestones.goal.goalId
                        _goalTitle.value = goalTitle.value.copy(
                            text = goalWithMilestones.goal.goal
                        )
                        _typeOfMindset.value = typeOfMindset.value.copy(
                            text = goalWithMilestones.goal.typeOfMindset
                        )
                        _goalColor.value = goalColor.value.copy(
                            color = goalWithMilestones.goal.color
                        )
                        goalWithMilestones.milestones.forEach {
                            milestonesListState.add(
                                singleMilestoneState.value.copy(
                                    milestone = it.milestone,
                                    milestoneId = it.milestoneId,
                                    parentGoal = it.parentGoal,
                                    dateCreated = it.dateCreated
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
                _goalTitle.value = goalTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditGoalEvent.EnterTypeOfMindset -> {
                _typeOfMindset.value = typeOfMindset.value.copy(
                    text = event.value
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
                    parentGoal = goalTitle.value.text,
                    dateCreated = System.currentTimeMillis()
                )
            }

            is AddEditGoalEvent.DeleteMilestone -> {
                milestonesListState.remove(event.milestone)
            }

            is AddEditGoalEvent.OnChangeColor -> {
                _goalColor.value = goalColor.value.copy(
                    color = event.color
                )
            }

            is AddEditGoalEvent.SaveGoal -> {
                viewModelScope.launch {
                    try {
                        goalUseCases.addGoal(
                            Goal(
                                goal = goalTitle.value.text,
                                typeOfMindset = typeOfMindset.value.text,
                                dateCreated = System.currentTimeMillis(),
                                color = goalColor.value.color,
                                isClicked = false,
                                lastClick = 0,
                                totalDays = 0,
                                goalId = currentGoalId
                            )
                        )
                        goalUseCases.addMilestoneList(
                            milestonesListState.map {
                                it.toMilestone()
                            }
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
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
        object SaveNote : UiEvent()
    }
}