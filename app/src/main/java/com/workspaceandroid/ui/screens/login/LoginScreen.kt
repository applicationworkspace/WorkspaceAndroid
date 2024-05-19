package com.workspaceandroid.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.navigation.Screen
import com.workspaceandroid.navigation.navGraph.Graph
import com.workspaceandroid.ui.screens.login.LoginContract.Effect
import com.workspaceandroid.ui.screens.login.LoginContract.Event
import com.workspaceandroid.ui.screens.login.LoginContract.LoginState
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_32
import com.workspaceandroid.ui.theme.offset_8
import com.workspaceandroid.ui.theme.radius_8
import com.workspaceandroid.ui.widgets.HyperlinkText
import com.workspaceandroid.ui.widgets.InputTextField
import com.workspaceandroid.ui.widgets.PasswordInputTextField

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        state = viewModel.viewState.collectAsState().value,
        onLoginClick = { email, password ->
            viewModel.setEvent(
                Event.OnLoginButtonClicked(
                    email,
                    password
                )
            )
        },
        onSignUpClick = { viewModel.setEvent(Event.OnSignUpButtonClicked) }
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowToast -> {
//                    scaffoldState.snackbarHostState.showSnackbar(
//                        message = snackbarMessage,
//                        duration = SnackbarDuration.Short
//                    )
                }
                is Effect.Navigation.ToMain -> {
                    navController.navigate(Graph.MAIN_ROUTE) {
                        popUpTo(Graph.MAIN_ROUTE)
                    }
                }
                Effect.Navigation.ToRegistration -> {
                    navController.navigate(route = Screen.SignUp.route)
                }
            }
        }
    }

}

@Composable
fun LoginScreen(
    state: LoginState,
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit
) {

    val inputValueEmail = rememberSaveable { mutableStateOf("sasha@gmail.com") }
    val inputValuePassword = rememberSaveable { mutableStateOf("12345678") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    if (state is LoginState.ValidationError) {
        isEmailError = true //TODO validation
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            Modifier.padding(offset_16)
        ) {
            val (headerText, inputEmail, inputPassword, loginButton, forgotPassword, signUpLink) = createRefs()

            Column(modifier = Modifier
                .constrainAs(headerText) {
                    top.linkTo(parent.top, margin = offset_32)
                    start.linkTo(parent.start)
                }) {
                Text(
                    text = stringResource(R.string.auth_header),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(offset_12))
                Text(
                    text = stringResource(R.string.auth_sub_header)
                )
            }

            InputTextField(
                modifier = Modifier
                    .constrainAs(inputEmail) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = inputValueEmail.value,
                label = stringResource(id = R.string.auth_email),
                onInputChanged = {
                    inputValueEmail.value = it
                    isEmailError = false
                },
                inputType = KeyboardType.Email,
                placeholderText = stringResource(id = R.string.auth_email_hint),
                isError = isEmailError
            )
            PasswordInputTextField(
                modifier = Modifier
                    .constrainAs(inputPassword) {
                        top.linkTo(inputEmail.bottom, margin = offset_16)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = inputValuePassword.value,
                label = stringResource(id = R.string.auth_password),
                onInputChanged = {
                    inputValuePassword.value = it
                    isPasswordError = false
                },
                placeholderText = stringResource(id = R.string.auth_password_hint),
                isError = isPasswordError
            )
            Text(
                modifier = Modifier
                    .constrainAs(forgotPassword) {
                        top.linkTo(inputPassword.bottom, margin = offset_16)
                        end.linkTo(parent.end)
                    },
                text = stringResource(R.string.auth_forgot_password)
            )

            LoginButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(loginButton) {
                        top.linkTo(forgotPassword.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onButtonClick = {
                    onLoginClick.invoke(inputValueEmail.value, inputValuePassword.value)
                }
            )
            HyperlinkText(
                modifier = Modifier
                    .constrainAs(signUpLink) {
                        bottom.linkTo(parent.bottom, margin = offset_16)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                fullText = stringResource(R.string.auth_dont_have_an_account)
                    .plus(stringResource(R.string.auth_register_now)),
                hyperLinks = mutableMapOf(stringResource(R.string.auth_register_now) to ""),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                linkTextColor = MaterialTheme.colorScheme.primary,
                clickListener = onSignUpClick
            )
        }
    }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Button(
        onClick = onButtonClick,
        modifier = modifier
            .padding(
                top = offset_32,
                bottom = offset_32
            ),
        shape = RoundedCornerShape(radius_8)
    ) {
        Text(
            modifier = Modifier.padding(top = offset_8, bottom = offset_8),
            text = stringResource(R.string.auth_login)
        )
    }
}

@Composable
@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginState.Success("Name"),
        onLoginClick = { _, _ -> },
        onSignUpClick = { }
    )
}