package com.workspaceandroid.ui.feature.groups

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.model.GroupUIModel
import com.workspaceandroid.model.mapper.GroupUIModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    private val groupUIModelMapper: GroupUIModelMapper
) : BaseViewModel<GroupsContract.Event, GroupsContract.State, GroupsContract.Effect>() {

    private var groupColors: List<Pair<String, Boolean>> = listOf(
        "#ffb5c0d0" to true,
        "#ffccd3ca" to false,
        "#fff5e8dd" to false,
        "#ffeed3d9" to false,
        "#ffb7cf93" to false,
        "#ff93cfa8" to false,
        "#ff93cfcf" to false,
        "#ff93a7cf" to false,
        "#ffcf93b2" to false,
        "#ffcf939a" to false
    )

    override fun setInitialState(): GroupsContract.State =
        GroupsContract.State().copy(isLoading = true)

    override fun handleEvents(event: GroupsContract.Event) {
        when(event) {
            is GroupsContract.Event.OnSaveButtonClicked -> addNewGroup(event.group)
            is GroupsContract.Event.OnDeleteGroupClicked -> deleteGroup(event.groupId)
            is GroupsContract.Event.OnColorPicked -> reselectColor(event.colorHex)
        }
    }

    init {
        viewModelScope.launch {
            val allGroups = collectionInteractor.getUserGroups().run(groupUIModelMapper::mapListTo)
            setState { copy(isLoading = false, groups = allGroups) }
        }
        setState { copy(colors = groupColors) }
    }

    private fun addNewGroup(newGroup: GroupUIModel) {
        viewModelScope.launch {
            val selectedColor =
                groupColors.firstOrNull { it.second }?.first?.replace("#", "") ?: DEFAULT_COLOR
            val newGroupResponse =
                collectionInteractor.addUserGroup(groupUIModelMapper.mapFromEntity(newGroup.copy(hexColor = selectedColor)))
                    .run(groupUIModelMapper::mapToEntity)
            setState { copy(
                isLoading = false,
                groups = listOf(newGroupResponse) + viewState.value.groups)
            }
        }
    }

    private fun deleteGroup(group: GroupUIModel) {
        viewModelScope.launch {
            collectionInteractor.deleteUserGroup(group.id)
            setState { copy(isLoading = false, groups = viewState.value.groups - group) }
        }
    }

    private fun reselectColor(colorHex: String) {
        groupColors = groupColors.map { it.copy(second = it.first == colorHex) }
        setState { copy(colors = groupColors) }
    }

    companion object {
        private const val DEFAULT_COLOR = "ffb7cf93"
    }
}