package com.foodapp.foodapp.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.foodapp.presentation.components.CustomButton
import com.foodapp.foodapp.presentation.components.CustomTextField
import com.foodapp.foodapp.presentation.components.OutlinedCustomButton
import com.foodapp.foodapp.presentation.register.RegisterIntent
import com.foodapp.foodapp.storage.TokenStorage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource


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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform), // Replace with your logo resource
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Email-id",
            value = state.email,
            onValueChange = { onEvent(LoginIntent.EmailChanged(it)) },
            type = "email"
        )

        CustomTextField(
            label = "Password",
            value = state.password,
            onValueChange = { onEvent(LoginIntent.PasswordChanged(it)) },
            type = "password"
        )


        CustomButton(
            text = "Login",
            onClick = { onEvent(LoginIntent.SubmitLogin(state.isUser)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text("Or", modifier = Modifier.padding(8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Google Sign-Up
        OutlinedCustomButton(
            onClick = { /* Google Login */ },
            text = "Login With Google"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Facebook Sign-Up
        OutlinedCustomButton(
            onClick = { /* Facebook Login */ },
            text = "Login With Facebook"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Don't have an account?",
            color = DarkGrey
        )
        Text(
            text = AnnotatedString("Sign Up Now", spanStyle = SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline)),
//            onClick / { /* Navigate to Sign Up */ },
            style = TextStyle(color = Black, fontWeight = FontWeight.Bold)
        )
    }
}
