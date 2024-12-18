package com.foodapp.foodapp.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.ui.text.input.KeyboardType


@Composable
fun LoginScreenRoot(
    viewModel: AuthLoginViewModel = koinViewModel(),
    onLoginClicked: () -> Unit,
    isUser: Boolean = true,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    state.isUser = isUser

    LoginScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LoginIntent.SubmitLogin -> {
                    viewModel.onEvent(event)
                    onLoginClicked()
                }
                else -> viewModel.onEvent(event)
            }
        },
        isUser = isUser,
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEvent: (LoginIntent) -> Unit,
    isUser: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (isUser) "User Login" else "Restaurant Login",
//            style = MaterialTheme3.typography.h5,
//            color = MaterialTheme3.colors.primary
        )

        TextField(
            value = state.email,
            onValueChange = { onEvent(LoginIntent.EmailChanged(it)) },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(top = 8.dp)
        )

        TextField(
            value = state.password,
            onValueChange = { onEvent(LoginIntent.PasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 8.dp)
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        state.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        state.message?.let {
            Text(
                text = it,
                color = Color.Green,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = { onEvent(LoginIntent.SubmitLogin(state.isUser)) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }
    }
}
