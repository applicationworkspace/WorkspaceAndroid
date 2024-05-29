package com.workspaceandroid.ui.feature.signup

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.workspaceandroid.R
import com.workspaceandroid.ui.feature.signup.SignUpContract.Effect
import com.workspaceandroid.ui.feature.signup.SignUpContract.Event
import com.workspaceandroid.ui.theme.light_gray2
import com.workspaceandroid.ui.theme.offset_12
import com.workspaceandroid.ui.theme.offset_16
import com.workspaceandroid.ui.theme.offset_32
import com.workspaceandroid.ui.theme.offset_64
import com.workspaceandroid.ui.theme.offset_8
import com.workspaceandroid.ui.theme.radius_8
import com.workspaceandroid.ui.theme.text_color_gray
import com.workspaceandroid.ui.theme.width_1
import com.workspaceandroid.ui.widgets.HyperlinkText
import com.workspaceandroid.ui.widgets.InputTextField
import com.workspaceandroid.ui.widgets.PasswordInputTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            SignUpScreen(
                state = viewModel.viewState.collectAsState().value,
                onSignUpClick = { email, password, passwordAgain ->
                    viewModel.setEvent(
                        Event.OnSignUpButtonClicked(
                            email,
                            password,
                            passwordAgain
                        )
                    )
                },
                onLoginLinkClick = {
                    viewModel.setEvent(Event.OnLoginLinkClicked)
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is Effect.ShowSnackBar -> {
                    localCoroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message.asString(context)
                        )
                    }
                }
                is Effect.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    state: SignUpContract.SignUpState,
    onSignUpClick: (String, String, String) -> Unit,
    onLoginLinkClick: () -> Unit
) {

    val inputValueEmail = rememberSaveable { mutableStateOf("") }
    val inputValuePassword = rememberSaveable { mutableStateOf("") }
    val inputValuePasswordAgain = rememberSaveable { mutableStateOf("") }
    val termsApproved = rememberSaveable { mutableStateOf(false) }

        ConstraintLayout(
            Modifier
                .padding(offset_16)
                .fillMaxSize()
        ) {
            val (headerText, inputEmail, inputPassword, inputPasswordAgain, buttonSignUp, loginLink,
                terms, separatorButtonGroup, buttonGoogleSignIn) = createRefs()

            Column(modifier = Modifier
                .constrainAs(headerText) {
                    top.linkTo(parent.top, margin = offset_32)
                    start.linkTo(parent.start)
                })
            {
                Text(
                    text = stringResource(R.string.auth_signup_header),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(offset_12))
                Text(
                    text = stringResource(R.string.auth_signup_sub_header)
                )
            }

            InputTextField(
                modifier = Modifier
                    .constrainAs(inputEmail) {
                        top.linkTo(headerText.bottom, margin = offset_64)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = inputValueEmail.value,
                label = stringResource(id = R.string.auth_email),
                onInputChanged = {
                    inputValueEmail.value = it
                },
                inputType = KeyboardType.Email,
                placeholderText = stringResource(id = R.string.auth_email_hint),
                isError = state is SignUpContract.SignUpState.ValidationError
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
                },
                placeholderText = stringResource(id = R.string.auth_password_hint)
            )

            PasswordInputTextField(
                modifier = Modifier
                    .constrainAs(inputPasswordAgain) {
                        top.linkTo(inputPassword.bottom, margin = offset_16)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                value = inputValuePasswordAgain.value,
                label = stringResource(id = R.string.auth_password_again),
                onInputChanged = {
                    inputValuePasswordAgain.value = it
                },
                placeholderText = stringResource(id = R.string.auth_password_again_hint)
            )

            Row(
                modifier = Modifier
                    .constrainAs(terms) {
                        top.linkTo(inputPasswordAgain.bottom)
                        start.linkTo(parent.start)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = termsApproved.value,
                    onCheckedChange = {
                        termsApproved.value = !termsApproved.value
                    }
                )
                HyperlinkText(
                    fullText = stringResource(id = R.string.auth_terms_conditions),
                    hyperLinks = mutableMapOf(
                        "умовами" to "https://google.com",
                        "положеннями" to "https://google.com"
                    ),
                    linkTextColor = MaterialTheme.colorScheme.primary
                )
            }

            SignUpButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(buttonSignUp) {
                        top.linkTo(terms.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                termsApproved.value,
                onButtonClick = {
                    onSignUpClick.invoke(
                        inputValueEmail.value,
                        inputValuePassword.value,
                        inputValuePasswordAgain.value
                    )
                }
            )

            Row(
                modifier = Modifier
                    .constrainAs(separatorButtonGroup) {
                        top.linkTo(buttonSignUp.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = light_gray2,
                    thickness = width_1
                )
                Text(
                    modifier = Modifier.padding(start = offset_8, end = offset_8),
                    text = stringResource(id = R.string.label_or).uppercase()
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = light_gray2,
                    thickness = width_1
                )
            }

            GoogleSignInButton(
                modifier = Modifier
                    .constrainAs(buttonGoogleSignIn) {
                        top.linkTo(separatorButtonGroup.bottom, margin = offset_8)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                signInClicked = {
                    //TODO google sign in
                }
            )

            HyperlinkText(
                modifier = Modifier
                    .constrainAs(loginLink) {
                        bottom.linkTo(parent.bottom, margin = offset_16)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                fullText = "${stringResource(R.string.auth_already_have_account)} ${stringResource(R.string.auth_login)}",
                hyperLinks = mutableMapOf(stringResource(R.string.auth_login) to ""),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                linkTextColor = MaterialTheme.colorScheme.primary,
                clickListener = onLoginLinkClick
            )
        }
}

@Composable
fun SignUpButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onButtonClick: () -> Unit
) {
    Button(
        onClick = onButtonClick,
        modifier = modifier
            .padding(
                top = offset_32,
                bottom = offset_32
            ),
        shape = RoundedCornerShape(radius_8),
        enabled = isEnabled
    ) {
        Text(
            modifier = Modifier.padding(top = offset_8, bottom = offset_8),
            text = stringResource(R.string.auth_sign_up)
        )
    }
}

@Composable
fun GoogleSignInButton(
    modifier: Modifier,
    signInClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .height(55.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = signInClicked
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(32.dp)
                        .padding(0.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_logo_google),
                    contentDescription = "google_logo"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterVertically),
                    text = stringResource(R.string.auth_sign_in_with_google),
                    color = text_color_gray, //TODO create style
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )
            }
        }
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
fun SignUpScreenPreview() {
    SignUpScreen(
        state = SignUpContract.SignUpState.Loading,
        onSignUpClick = { _, _, _ -> },
        onLoginLinkClick = { }
    )
}