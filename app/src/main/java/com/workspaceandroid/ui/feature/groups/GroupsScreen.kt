package com.workspaceandroid.ui.feature.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.model.GroupUIModel
import com.workspaceandroid.ui.theme.elevation_2
import com.workspaceandroid.ui.theme.icon_size_32
import com.workspaceandroid.ui.theme.light_gray
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_8
import com.workspaceandroid.ui.theme.radius_16
import com.workspaceandroid.ui.theme.radius_8
import com.workspaceandroid.ui.theme.text_color_gray
import com.workspaceandroid.ui.theme.white
import com.workspaceandroid.ui.theme.width_1
import com.workspaceandroid.ui.widgets.TextInput
import com.workspaceandroid.ui.widgets.ToolbarComponent

@Composable
fun GroupsScreen(
    navController: NavHostController,
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    GroupsScreen(
        state = viewModel.viewState.collectAsState().value,
        onBackClick = { navController.popBackStack() },
        onEditGroupClick = { },
        onDeleteGroupClick = { viewModel.setEvent(GroupsContract.Event.OnDeleteGroupClicked(it)) },
        onAddGroupClick = { viewModel.setEvent(GroupsContract.Event.OnSaveButtonClicked(it)) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    state: GroupsContract.State,
    onBackClick: () -> Unit,
    onAddGroupClick: (GroupUIModel) -> Unit,
    onEditGroupClick: (GroupUIModel) -> Unit,
    onDeleteGroupClick: (GroupUIModel) -> Unit,
) {
    Column {
        ToolbarComponent(
            text = stringResource(R.string.collection_title),
            onBackClick = onBackClick
        )
        LazyColumn(modifier = Modifier.padding(horizontal = offset_16)) {
            item {
                NewGroupCard(
                    modifier = Modifier,
                    onAddClick = onAddGroupClick
                )
            }
            items(state.groups) { group ->
                GroupCard(
                    group = group,
                    onCardClick = {},
                    onDeleteClick = onDeleteGroupClick,
                    onEditClick = onEditGroupClick
                )
            }
        }
    }
}


@Composable
fun NewGroupCard(
    modifier: Modifier,
    onAddClick: (GroupUIModel) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation_2),
        shape = RoundedCornerShape(radius_16),
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = white
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(offset_16)
        ) {
            TextInput(
                onInputChanged = { name = it },
                label = stringResource(id = R.string.label_name),
                placeholderText = stringResource(id = R.string.groups_hint_enter_name)
            )

            TextInput(
                onInputChanged = { description = it },
                label = stringResource(id = R.string.label_description),
                placeholderText = stringResource(id = R.string.groups_hint_enter_description)
            )
            Button(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = offset_8),
                onClick = {
                    //TODO refactor new model for the input
                    onAddClick.invoke(
                        GroupUIModel(id = -1, name = name, description = description, hexColor = "40E0D0", phrases = emptyList(), isSelected = false)
                    )
                },
                shape = RoundedCornerShape(radius_8)
            ) {
                Text(
                    modifier = Modifier.padding(top = offset_8, bottom = offset_8),
                    text = stringResource(R.string.groups_save)
                )
            }
        }
    }
}

@Composable
fun GroupCard(
    group: GroupUIModel,
    onCardClick: (GroupUIModel) -> Unit,
    onEditClick: (GroupUIModel) -> Unit,
    onDeleteClick: (GroupUIModel) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = elevation_2),
        shape = RoundedCornerShape(radius_16),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = offset_8)
            .clickable { onCardClick.invoke(group) },
        colors = CardDefaults.cardColors(
            containerColor = white
        )
    ) {
        Column(Modifier.padding(horizontal = offset_16, vertical = offset_12)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = group.name,
                    color = text_color_gray,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .size(icon_size_32)
                        .clip(CircleShape)
                        .background(Color(android.graphics.Color.parseColor("#FF${group.hexColor}")))
                )
            }
            Text(
                text = group.description,
                color = text_color_gray,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = offset_16)
                    .height(width_1),
                color = light_gray
            )
            Row {
//                FilledIconButton(
//                    colors = IconButtonDefaults.filledIconButtonColors(
//                        containerColor = light_gray
//                    ),
//                    onClick = { onEditClick.invoke(group) }) {
//                    Icon(Icons.Outlined.Edit, contentDescription = "Localized description")
//                }

                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = light_gray
                    ),
                    onClick = { onDeleteClick.invoke(group) }) {
                    Icon(Icons.Outlined.Delete, contentDescription = "Localized description")
                }
            }
        }

    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun GroupsScreenPreview() {
    GroupsScreen(
        state = GroupsContract.State(
            groups = listOf(
                GroupUIModel(
                    id = 1,
                    name = "Group1",
                    description = "group1_description",
                    hexColor = "c0d6e4",
                    phrases = emptyList(),
                    isSelected = false
                )
            )
        ),
        onBackClick = {},
        onAddGroupClick = {},
        onEditGroupClick = {},
        onDeleteGroupClick = {}
    )
}