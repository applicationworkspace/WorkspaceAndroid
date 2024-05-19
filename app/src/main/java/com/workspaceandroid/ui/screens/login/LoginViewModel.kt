package com.workspaceandroid.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.domain.interactors.AuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val authInteractor: AuthInteractor)
    : BaseViewModel<LoginContract.Event, LoginContract.LoginState, LoginContract.Effect>() {

    override fun setInitialState(): LoginContract.LoginState = LoginContract.LoginState.Idle

    override fun handleEvents(event: LoginContract.Event) = when (event) {
        is LoginContract.Event.OnLoginButtonClicked ->  {
            performAuthValidation(event.email, event.password)
        }
        LoginContract.Event.OnSignUpButtonClicked -> setEffect {
            LoginContract.Effect.Navigation.ToRegistration
        }
    }

    private fun makeAuthorizationRequest(email: String, password: String) {
//        setState {
//            copy(isLoading = true, token = null)
//        }
        viewModelScope.launch {
            authInteractor.performAuth(email, password)
            setEffect { LoginContract.Effect.Navigation.ToMain("userLogin") }
        }
    }

    private fun performAuthValidation(email: String, password: String) {
        if (email.isBlank()) {
            setState { LoginContract.LoginState.ValidationError("Invalid email") }
            return
        }

        if (password.isBlank()) {
            setEffect { LoginContract.Effect.ShowToast("Invalid password") }
            return
        }
        makeAuthorizationRequest(email, password)
    }
}