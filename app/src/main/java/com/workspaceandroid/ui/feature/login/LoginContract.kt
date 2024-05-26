package com.workspaceandroid.ui.feature.login

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        data class OnLoginButtonClicked(val email: String, val password: String) : Event()
        object OnSignUpButtonClicked : Event()
    }

    sealed class LoginState: ViewState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val name : String) : LoginState()
        data class Error(val errorMessage : String) : LoginState()
        data class ValidationError(val errorMessage : String) : LoginState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowToast(val message: String) : Effect()

        sealed class Navigation : Effect() {
            data class ToMain(val userLogin: String): Navigation()
            object ToRegistration: Navigation()
        }
    }
}