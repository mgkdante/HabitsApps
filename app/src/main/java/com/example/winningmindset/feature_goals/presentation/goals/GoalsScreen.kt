package com.example.winningmindset.feature_goals.presentation.goals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.winningmindset.feature_goals.presentation.goals.components.GoalItem
import com.example.winningmindset.feature_goals.presentation.goals.components.OrderSection
import com.example.winningmindset.feature_goals.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    navController: NavController,
    viewModel: GoalsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddEditScreen.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.goalsWithMilestones.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "No Resolutions Added",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
            } else {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Goals",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        IconButton(onClick = {
                            viewModel.onEvent(GoalsEvent.ToggleOrderSection)
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Sort"
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = state.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            goalOrder = state.goalOrder,
                            onOrderChange = {
                                viewModel.onEvent(GoalsEvent.Order(it))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(state.goalsWithMilestones) { goalWithMilestones ->
                    GoalItem(
                        goal = goalWithMilestones.goal,
                        isClicked = goalWithMilestones.goal.isClicked,
                        milestone = goalWithMilestones.milestones,
                        onDelete = {
                            viewModel.onEvent(
                                GoalsEvent.DeleteGoal(
                                    goalWithMilestones.goal,
                                    goalWithMilestones.milestones
                                )
                            )
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note Deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(GoalsEvent.RestoreGoal)
                                }
                            }
                        },
                        recordClick = { /*Do NOTHING FOR THE MOMENT*/ },
                        onClickEdit = {
                            navController.navigate(
                                Screen.AddEditScreen.route +
                                        "?goalId=${goalWithMilestones.goal.goalId}&goalColor=${goalWithMilestones.goal.color}"
                            )
                        }
                    )
                }
            }
        }
    }
}