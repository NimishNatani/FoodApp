package com.foodapp.foodapp.presentation.login

import com.foodapp.foodapp.presentation.register.RegisterIntent

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class SubmitLogin(val isUser:Boolean) : LoginIntent()
}

