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
        "#ffD1FADF" to true,
        "#ffFEDF89" to false,
        "#ffFDDCAB" to false,
        "#ffFDA29B" to false,
        "#ffFECDD6" to false,
        "#ffBDB4FE" to false,
        "#ffA4BCFD" to false,
        "#ffB2DDFF" to false,
        "#ffD5D9EB" to false,
        "#ff98A2B3" to false
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
        private const val DEFAULT_COLOR = "ffD1FADF"
    }
}