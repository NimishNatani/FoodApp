package com.foodapp.foodapp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthRegisterViewModel(private val authRepository: AuthRepository,private val tokenStorage: TokenStorage) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = intent.name)
            }

            is RegisterIntent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = intent.email)
            }

            is RegisterIntent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = intent.password)
            }

            is RegisterIntent.ConfirmPasswordChanged -> {
                _uiState.value = _uiState.value.copy(confirmPassword = intent.confirmPassword)
            }

            is RegisterIntent.SubmitRegister -> {

                performRegister(intent.isUser)
            }
        }
    }

     private fun performRegister(isUser: Boolean) {
        val state = _uiState.value

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            // Simulate network call
            if (state.password == state.confirmPassword && state.email.contains("@")) {
                val result = authRepository.register(state.name, state.email, state.password, isUser)
                result.onSuccess { authToken ->
                    println(authToken)
                    tokenStorage.saveToken(authToken.token)
                    _uiState.update { it.copy(isLoading = false, result = authToken.token, message = authToken.message) }
                }.onError { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.toString()+"comeafter") }
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Registration failed: ${if (isUser) "User" else "Restaurant"}"
                )
            }
        }
    }
}
