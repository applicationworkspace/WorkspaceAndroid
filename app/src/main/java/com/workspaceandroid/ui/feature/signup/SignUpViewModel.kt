package com.workspaceandroid.ui.feature.signup

import com.workspaceandroid.R
import com.workspaceandroid.base.BaseViewModel
import com.workspaceandroid.data.common.ITimeHelper
import com.workspaceandroid.domain.interactors.AuthInteractor
import com.workspaceandroid.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val timeHelper: ITimeHelper
) : BaseViewModel<SignUpContract.Event, SignUpContract.SignUpState, SignUpContract.Effect>() {

    override fun setInitialState(): SignUpContract.SignUpState = SignUpContract.SignUpState.Idle

    override fun handleEvents(event: SignUpContract.Event) {
        when (event) {
            is SignUpContract.Event.OnSignUpButtonClicked -> launchRegistrationProcess(
                event.email,
                event.password,
                event.passwordAgain
            )
            SignUpContract.Event.OnLoginLinkClicked -> setEffect { SignUpContract.Effect.Navigation.Back }
        }
    }

    private fun launchRegistrationProcess(
        email: String,
        password: String,
        passwordAgain: String
    ) {
        val isCredentialsValid = validateSignUpAttempt(email, password, passwordAgain)
        if (isCredentialsValid) {
            setState {
                SignUpContract.SignUpState.Loading
            }
        }
    }

    private fun validateSignUpAttempt(
        email: String,
        password: String,
        passwordAgain: String
    ): Boolean {

        val emailAddressPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

        if (email.isEmpty()) {
            setState {
                SignUpContract.SignUpState.ValidationError(
                    UiText.StringResource(R.string.auth_email_empty),
                    SignUpInputSource.EMAIL
                )
            }
            setEffect {
                SignUpContract.Effect.ShowSnackBar(
                    UiText.StringResource(R.string.auth_email_empty)
                )
            }
            return false
        } else if (!emailAddressPattern.matcher(email).matches()) {
            setState {
                SignUpContract.SignUpState.ValidationError(
                    UiText.StringResource(R.string.auth_email_wrong),
                    SignUpInputSource.EMAIL
                )
            }
            setEffect {
                SignUpContract.Effect.ShowSnackBar(
                    UiText.StringResource(R.string.auth_email_wrong)
                )
            }
            return false
        }
        return true
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}