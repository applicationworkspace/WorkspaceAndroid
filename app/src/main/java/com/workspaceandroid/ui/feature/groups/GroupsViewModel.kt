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

    override fun setInitialState(): GroupsContract.State = GroupsContract.State().copy(isLoading = true)

    override fun handleEvents(event: GroupsContract.Event) {
        when(event) {
            is GroupsContract.Event.OnSaveButtonClicked -> addNewGroup(event.group)
            is GroupsContract.Event.OnDeleteGroupClicked -> deleteGroup(event.groupId)
        }
    }

    init {
        viewModelScope.launch {
            val allGroups = collectionInteractor.getUserGroups().run(groupUIModelMapper::mapListTo)
            setState { copy(isLoading = false, groups = allGroups) }
        }
    }

    private fun addNewGroup(newGroup: GroupUIModel) {
        viewModelScope.launch {
            collectionInteractor.addUserGroup(groupUIModelMapper.mapFromEntity(newGroup))
            setState { copy(
                isLoading = false,
                groups = listOf(newGroup) + viewState.value.groups)
            }
        }
    }

    private fun deleteGroup(group: GroupUIModel) {
        viewModelScope.launch {
            collectionInteractor.deleteUserGroup(group.id)
            setState { copy(isLoading = false, groups = viewState.value.groups - group) }
        }
    }


}