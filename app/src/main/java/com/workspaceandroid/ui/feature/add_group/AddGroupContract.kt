package com.workspaceandroid.ui.feature.add_group

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.model.GroupUIModel

class AddGroupContract {

    sealed class Event : ViewEvent {
        data class OnSaveButtonClicked(val group: GroupUIModel) : Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            object Back: Navigation()
        }
    }
}