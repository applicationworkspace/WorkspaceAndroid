package com.workspaceandroid.ui.feature.groups

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.model.GroupUIModel

class GroupsContract {

    sealed class Event : ViewEvent {
        data class OnSaveButtonClicked(val group: GroupUIModel) : Event()
        data class OnDeleteGroupClicked(val groupId: GroupUIModel) : Event()
        data class OnColorPicked(val colorHex: String) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val groups: List<GroupUIModel> = emptyList(),
        val colors: List<Pair<String, Boolean>> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            object Back: Navigation()
        }
    }
}