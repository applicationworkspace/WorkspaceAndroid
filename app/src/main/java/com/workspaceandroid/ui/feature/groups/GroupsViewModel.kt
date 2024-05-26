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

    private var initialColors: List<Pair<String, Boolean>> = listOf(
        "#FFF5DAD2" to true,
        "#FFD37676" to false,
        "#FFF1EF99" to false,
        "#FFE1ACAC" to false,
        "#FFF6F193" to false,
        "#FFAD88C6" to false,
        "#FFA5DD9B" to false,
        "#FFBED7DC" to false,
        "#FFFF8080" to false,
        "#FF618264" to false
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
        setState { copy(colors = initialColors) }
    }

    private fun addNewGroup(newGroup: GroupUIModel) {
        viewModelScope.launch {
            val newGroupResponse =
                collectionInteractor.addUserGroup(groupUIModelMapper.mapFromEntity(newGroup))
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
        setState { copy(colors = initialColors.map { it.copy(second = it.first == colorHex) }) }
    }

}