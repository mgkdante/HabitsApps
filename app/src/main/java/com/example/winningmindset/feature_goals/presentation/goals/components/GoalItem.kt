package com.example.winningmindset.feature_goals.presentation.goals.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.winningmindset.feature_goals.domain.model.Goal
import com.example.winningmindset.feature_goals.domain.model.Milestone
import com.example.winningmindset.ui.theme.Shapes
import org.joda.time.DateTime
import org.joda.time.Days
import java.text.DateFormat


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoalItem(
    modifier: Modifier = Modifier,
    goal: Goal,
    isClicked: Boolean,
    milestone: List<Milestone>,
    onDelete: () -> Unit,
    onClickEdit: () -> Unit,
    recordClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(bottom = 4.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(Shapes.small.topStart))
            .border(2.dp, color = Color(goal.color))
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = Days.daysBetween(
                        DateTime(goal.lastClick).toLocalDate(),
                        DateTime(System.currentTimeMillis()).toLocalDate()
                    ).days.toString()
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = DateTime(System.currentTimeMillis()).toLocalDate().toString())
                Spacer(modifier = Modifier.weight(1f))
                Text(text = DateTime(goal.lastClick).toLocalDate().toString())
            }
            TopItem(
                goal = goal.goal,
                color = goal.color,
                recordClick = recordClick,
                isClicked = isClicked
            )
        }
        if (expanded) {
            ResolutionDetails(
                typeOfMindset = goal.typeOfMindset,
                color = goal.color
            )
            milestone.forEachIndexed { index, milestone ->
                MilestonesPerGoalItem(
                    milestone = milestone,
                    color = goal.color,
                    position = index.plus(1).toString()
                )
            }
            DateAddedInfo(goalCreatedDate = goal.dateCreated, color = goal.color)
            ActionButtons(color = goal.color, onDelete = onDelete, onClickEdit = onClickEdit)
        }
    }

}

@Composable
fun TopItem(
    goal: String,
    color: Long,
    modifier: Modifier = Modifier,
    recordClick: () -> Unit,
    isClicked: Boolean
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.heightIn(60.dp)) {
            ResolutionTitle(goal = goal, color = color)
            Spacer(modifier = Modifier.weight(1f))
            StreakIcon(
                recordClick = recordClick,
                clicked = isClicked,
                color = color
            )
        }
        Divider(color = Color(color))
    }
}

@Composable
fun ResolutionTitle(
    goal: String, modifier: Modifier = Modifier, color: Long
) {
    Text(
        text = goal,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(start = 8.dp),
        textAlign = TextAlign.Start,
        color = Color(color)
    )
}

@Composable
fun StreakIcon(
    modifier: Modifier = Modifier,
    recordClick: () -> Unit,
    clicked: Boolean,
    color: Long
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    IconButton(
        onClick = recordClick,
        interactionSource = interactionSource
    ) {
        Icon(
            modifier = modifier.size(35.dp),
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = "Record activity done",
            tint = if (clicked) {
                Color(color)
            } else MaterialTheme.colorScheme.onSurface
        )
    }


}

@Composable
fun ResolutionDetails(
    typeOfMindset: String,
    modifier: Modifier = Modifier,
    color: Long,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MindsetType(typeOfMindset = typeOfMindset, color = color)
        }
    }
}

@Composable
fun MindsetType(typeOfMindset: String, modifier: Modifier = Modifier, color: Long) {
    Column() {
        Text(
            text = "What type of person accomplishes this goal?",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(start = 4.dp, top = 4.dp),
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = typeOfMindset,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(start = 4.dp),
            textAlign = TextAlign.Left,
            color = Color(color)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "What steps are the steps to follow for this goal:",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(start = 4.dp, top = 4.dp),
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}


@Composable
fun MilestonesPerGoalItem(
    modifier: Modifier = Modifier,
    milestone: Milestone,
    position: String,
    color: Long
) {
    Row(modifier = modifier.padding(start = 4.dp)) {
        Text(text = "$position. ")
        Text(text = milestone.milestone, color = Color(color))
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DateAddedInfo(modifier: Modifier = Modifier, goalCreatedDate: Long, color: Long) {
    val dateCreated = DateTime(goalCreatedDate).withTimeAtStartOfDay().toDate()
    val dateString = DateFormat.getDateInstance().format(dateCreated)
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = dateString, modifier = modifier.padding(end = 4.dp), color = Color(color))
    }
}

@Composable
fun ActionButtons(
    color: Long,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit
) {
    Row {
        Spacer(modifier = modifier.weight(1f))
        EditButton(color = color, modifier = Modifier.padding(8.dp), onClickEdit = onClickEdit)
        DeleteButton(color = color, onDelete = onDelete, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun DeleteButton(modifier: Modifier = Modifier, color: Long, onDelete: () -> Unit) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    IconButton(onClick = { deleteConfirmationRequired = true }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Goal",
            tint = Color(color)
        )
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDelete()
            },
            onDeleteCancel = { deleteConfirmationRequired = false },
            color = Color(color)
        )
    }
}


@Composable
fun EditButton(modifier: Modifier = Modifier, color: Long, onClickEdit: () -> Unit) {
    IconButton(onClick = onClickEdit) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Goal",
            tint = Color(color)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Attention") },
        text = { Text("Are you sure you want to delete this goal?") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(
                    text = "No", color = color
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(
                    text = "Yes",
                    color = color
                )
            }
        }
    )
}