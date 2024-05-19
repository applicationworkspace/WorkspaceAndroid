package com.workspaceandroid.ui.screens.signup

import com.workspaceandroid.base.ViewEvent
import com.workspaceandroid.base.ViewSideEffect
import com.workspaceandroid.base.ViewState
import com.workspaceandroid.utils.UiText

class SignUpContract {

    sealed class Event : ViewEvent {
        data class OnSignUpButtonClicked(
            val email: String,
            val password: String,
            val passwordAgain: String
        ) : Event()
        object OnLoginLinkClicked : Event()
    }

    sealed class SignUpState : ViewState {
        object Idle : SignUpState()
        object Loading : SignUpState()
        data class Success(val name: String) : SignUpState()
        data class Error(val errorMessage: String) : SignUpState()
        data class ValidationError(
            val message: UiText,
            val wrongSource: SignUpInputSource
        ) : SignUpState()
    }

    sealed class Effect : ViewSideEffect {
        data class ShowSnackBar(val message: UiText) : Effect()

        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }
}

enum class SignUpInputSource {
    EMAIL, PASSWORD, PASSWORD_AGAIN
}