package com.foodapp.foodapp.presentation.register


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.LightGrey
import com.foodapp.foodapp.presentation.components.CustomButton
import com.foodapp.foodapp.presentation.components.CustomTextField
import com.foodapp.foodapp.presentation.components.OutlinedCustomButton
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: AuthRegisterViewModel = koinViewModel(),
    onRegisterClicked: () -> Unit,
    onLogin: () -> Unit,
    isUser: Boolean = true,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    state.isUser = isUser

    LaunchedEffect(state.result) {
        if (state.result.isNotEmpty()) {
            onRegisterClicked()
        }
    }

    RegisterScreen(
        state = state,
        onLogin = {onLogin()},
        onEvent = { event ->
            viewModel.onEvent(event)
        },
        isUser = isUser,
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    state: RegisterUiState,
    onLogin:()->Unit,
    onEvent: (RegisterIntent) -> Unit,
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
            label = if(isUser) "Username" else "Restaurant Name",
            value = state.name,
            onValueChange = { onEvent(RegisterIntent.NameChanged(it)) },
            type = "text"
        )

        CustomTextField(
            label = "Email-id",
            value = state.email,
            onValueChange = { onEvent(RegisterIntent.EmailChanged(it)) },
            type = "email"
        )

        CustomTextField(
            label = "Password",
            value = state.password,
            onValueChange = { onEvent(RegisterIntent.PasswordChanged(it)) },
            type = "password"
        )
        CustomTextField(
            label = "Confirm Password",
            value = state.confirmPassword,
            onValueChange = { onEvent(RegisterIntent.ConfirmPasswordChanged(it)) },
            type = "password"
        )

       CustomButton(
           text = "Register",
           onClick = { onEvent(RegisterIntent.SubmitRegister(state.isUser)) }
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
            text = "Sign Up With Google"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Facebook Sign-Up
        OutlinedCustomButton(
            onClick = { /* Facebook Login */ },
            text = "Sign Up With Facebook"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Already have an account?",
            color = DarkGrey
        )
        Text(
            text = AnnotatedString("Login Now", spanStyle = SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline)),
//            onClick / { /* Navigate to Sign Up */ },
            modifier = Modifier.clickable { onLogin() },
            style = TextStyle(color = Black, fontWeight = FontWeight.Bold)
        )


    }
}
