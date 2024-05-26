package com.workspaceandroid.ui.feature.add_group

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.CollectionInteractor
import com.workspaceandroid.domain.models.phrase.PhraseInput
import com.workspaceandroid.model.GroupUIModel
import com.workspaceandroid.model.mapper.GroupUIModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    private val groupUIModelMapper: GroupUIModelMapper
) : BaseViewModel<AddGroupContract.Event, AddGroupContract.State, AddGroupContract.Effect>() {

    override fun setInitialState(): AddGroupContract.State = AddGroupContract.State().copy(isLoading = true)

    override fun handleEvents(event: AddGroupContract.Event) {
        when (event) {
            is AddGroupContract.Event.OnSaveButtonClicked -> saveGroup(event.group)
        }
    }

    private fun saveGroup(group: GroupUIModel) {
        val newGroup = groupUIModelMapper.mapFromEntity(group)
        viewModelScope.launch { collectionInteractor.addUserGroup(newGroup) }
    }

}