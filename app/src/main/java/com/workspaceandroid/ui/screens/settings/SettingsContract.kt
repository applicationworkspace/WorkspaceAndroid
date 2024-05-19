package com.workspaceandroid.ui.screens.settings

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState

class SettingsContract {

    sealed class Event : ViewEvent {
        data class OnButtonClick(val property: String) : Event()
    }

    sealed class SettingsState: ViewState {
        object Idle : SettingsState()
        object Loading : SettingsState()
        data class Success(val name : String) : SettingsState()
        data class Error(val errorMessage : String) : SettingsState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToMain(val userLogin: String): Navigation() //TODO remove
        }
    }

}