package com.example.winningmindset.feature_goals.presentation.add_edit_goals

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.winningmindset.feature_goals.domain.model.Goal.GoalColor.colors
import com.example.winningmindset.feature_goals.presentation.ResolutionTopAppBar
import com.example.winningmindset.feature_goals.presentation.util.Screen
import com.example.winningmindset.ui.theme.Shapes
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditGoalScreen(
    navController: NavController,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: AddEditGoalViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        appBarState
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditGoalViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditGoalViewModel.UiEvent.SaveGoal -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ResolutionTopAppBar(
                scrollBehavior = scrollBehavior,
                title = Screen.AddEditScreen.title,
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        DataEntryBody(
            milestoneList = viewModel.milestonesListState,
            milestoneState = viewModel.singleMilestoneState.value,
            onSaveClick = {
                viewModel.onEvent(AddEditGoalEvent.SaveGoal)
            },
            modifier = Modifier.padding(padding),
            color = viewModel.goal.value.color
        )
    }
}


@Composable
fun DataEntryBody(
    milestoneState: MilestoneState,
    milestoneList: List<MilestoneState>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Long
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            DataEntryForm(
                milestoneState = milestoneState,
                milestoneList = milestoneList,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color)
                )
            ) {
                Text("Save")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataEntryForm(
    modifier: Modifier = Modifier,
    milestoneState: MilestoneState,
    milestoneList: List<MilestoneState>,
    viewModel: AddEditGoalViewModel = hiltViewModel()
) {
    val goal = viewModel.goal
    val milestone = viewModel.singleMilestoneState
    val currentColor = viewModel.goal.value.color

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = goal.value.goal,
            label = { Text(text = "What is your goal?") },
            onValueChange = { viewModel.onEvent(AddEditGoalEvent.EnterGoal(it)) },
            placeholder = {
                Text(
                    text = "Ex: Run a marathon, start a business, eat healthier, learn a new language, etc",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 90.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(currentColor),
                focusedLabelColor = Color(currentColor)
            )
        )
    }
    Spacer(modifier = modifier.height(16.dp))
    OutlinedTextField(
        value = goal.value.typeOfMindset,
        label = { Text(text = "What type of person can accomplish this goal?") },
        onValueChange = { viewModel.onEvent(AddEditGoalEvent.EnterTypeOfMindset(it)) },
        placeholder = {
            Text(
                text = "An organized person, an athletic person, a hardworking person, etc",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        singleLine = false,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(viewModel.goal.value.color),
            focusedLabelColor = Color(viewModel.goal.value.color)
        )
    )
    Spacer(modifier = modifier.height(16.dp))
    EnterMilestone(
        milestone = milestone.value.milestone,
        onValueChanged = { viewModel.onEvent(AddEditGoalEvent.EnterMilestone(it)) },
        onValueAddedToList = {
            if (milestoneState.milestone != "") {
                viewModel.onEvent(
                    AddEditGoalEvent.SaveMilestoneToList(milestone.value.toMilestone())
                )
            }
        },
        color = currentColor
    )
    MilestoneList(milestoneList = milestoneList)
    Spacer(modifier = modifier.height(16.dp))
    ColorButton(
        colorList = colors,
        onColorSelected = {
            viewModel.onEvent(AddEditGoalEvent.OnChangeColor(it.toArgb().toLong()))
        },
        color = viewModel.goal.value.color
    )
}

@Composable
fun MilestoneList(
    milestoneList: List<MilestoneState>,
) {
    milestoneList.forEachIndexed { index, milestone ->
        MilestoneListItem(
            milestoneState = milestone,
            position = index.plus(1).toString(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterMilestone(
    milestone: String,
    onValueChanged: (String) -> Unit,
    onValueAddedToList: () -> Unit,
    color: Long
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier
                    .heightIn(min = 90.dp)
                    .weight(1f),
                value = milestone,
                onValueChange = onValueChanged,
                label = { Text("Let's add some baby steps") },
                placeholder = {
                    Text(
                        "Ex: Run, read or learn for 5 minutes each day",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(color),
                    focusedLabelColor = Color(color)
                )
            )
            IconButton(onClick = onValueAddedToList) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Step")
            }
        }
        Spacer(modifier = Modifier.heightIn(16.dp))
    }
}

@Composable
fun MilestoneListItem(
    modifier: Modifier = Modifier,
    milestoneState: MilestoneState,
    position: String,
    viewModel: AddEditGoalViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.height(75.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color(viewModel.goal.value.color))
                    .fillMaxSize()
                    .weight(0.3f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = "$position  ",
                )
            }
            Box(
                modifier = Modifier
                    .border(1.dp, color = Color(viewModel.goal.value.color))
                    .weight(0.7f)
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = milestoneState.milestone
                )
            }
            IconButton(onClick = {
                viewModel.onEvent(
                    AddEditGoalEvent.DeleteMilestone(
                        milestoneState
                    )
                )
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove Item")
            }
        }
    }
}


@Composable
fun ColorButton(
    onColorSelected: (Color) -> Unit,
    colorList: List<Color>,
    color: Long
) {
    var isColorPickerOpen by remember {
        mutableStateOf(false)
    }

    var currentlySelected by remember { mutableStateOf(Color(color)) }

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Shapes.small.topEnd))
            .height(90.dp),
        onClick = { isColorPickerOpen = true },
        shape = Shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Select color",
                color = Color(color),
                style = MaterialTheme.typography.bodyLarge
            )
            Canvas(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(20))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onBackground,
                        RoundedCornerShape(20)
                    )
                    .background(color = currentlySelected)
            ) {}
        }
    }
    if (isColorPickerOpen) {
        ColorDialog(
            onDismiss = { isColorPickerOpen = false },
            onColorSelected = {
                currentlySelected = it
                onColorSelected(it)
            },
            colorList = colorList,
            color = color
        )
    } else onColorSelected(currentlySelected)
}


@Composable
private fun ColorDialog(
    colorList: List<Color>,
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit, // when the save button is clicked
    color: Long
) {
    val gridState = rememberLazyGridState()

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState
            ) {
                items(colorList) { color ->
                    Canvas(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(color)
                        .requiredSize(70.dp)
                        .clickable {
                            onColorSelected(color)
                            onDismiss()
                        }
                    ) {}
                }
            }
        },
        confirmButton = {}
    )
}