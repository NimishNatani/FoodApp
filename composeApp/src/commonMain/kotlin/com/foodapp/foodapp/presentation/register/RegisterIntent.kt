package com.foodapp.foodapp.presentation.register

import kotlinproject.composeapp.generated.resources.Res

sealed class RegisterIntent {
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterIntent()
    data class SubmitRegister(val isUser:Boolean) : RegisterIntent()
}
