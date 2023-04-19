@file:OptIn(ExperimentalComposeUiApi::class)

package com.merttoptas.devnottestingdemo.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.merttoptas.devnottestingdemo.R
import com.merttoptas.devnottestingdemo.ui.components.MainAppScaffold
import com.merttoptas.devnottestingdemo.ui.theme.DevnotTestingDemoTheme

/**
 * Created by mertcantoptas on 14.04.2023
 */

@Composable
internal fun LoginScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        loginUiState,
        modifier,
        onPasswordChanged = viewModel::onPasswordChanged,
        onUsernameChanged = viewModel::onUserNameChanged,
        onLogin = viewModel::onLoginClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginUiState: LoginUiState,
    modifier: Modifier = Modifier,
    onUsernameChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLogin: () -> Unit,
) {
    MainAppScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.login_screen_title),
                        modifier = Modifier.testTag("login_screen_title")
                    )
                },
            )
        },
    ) {
        Content(Modifier, loginUiState, onUsernameChanged, onPasswordChanged, onLogin)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val keyboardController =
            androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        if (loginUiState.isLoading) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .testTag("progress_bar")
                        .width(100.dp)
                        .height(100.dp),
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }
        Text(
            text = "Login",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("login_text")
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .wrapContentWidth()
                .testTag("username_text_field"),
            placeholder = {
                Text(text = "Please enter your username")
            },
            value = loginUiState.userName,
            singleLine = true,
            isError = loginUiState.isErrorUserName,
            onValueChange = onUsernameChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            supportingText = {
                if (loginUiState.isErrorUserName) {
                    Text(
                        text = "Please fill the user name field",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.testTag("error_username_text")
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }),

            )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .wrapContentWidth()
                .testTag("password_text_field"),
            placeholder = {
                Text(text = "Please enter your password")
            },
            value = loginUiState.password,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            isError = loginUiState.isErrorPassword,
            supportingText = {
                if (loginUiState.isErrorPassword) {
                    Text(
                        text = "Please fill the password field",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }),
            onValueChange = onPasswordChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password Icon",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            onClick = onLogin,
            modifier = Modifier
                .wrapContentWidth()
                .testTag("login_button")
        ) {
            Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    DevnotTestingDemoTheme {
        Content(modifier = Modifier, LoginUiState("", "", false), {}, {}, {})
    }
}