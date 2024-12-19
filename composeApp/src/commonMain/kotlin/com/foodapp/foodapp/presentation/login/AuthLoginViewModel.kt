package com.foodapp.foodapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.storage.TokenStorage

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthLoginViewModel(private val authRepository: AuthRepository,private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(message = tokenStorage.getToken()))
    val uiState =_uiState.asStateFlow()


    fun onEvent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = intent.email)
            }
            is LoginIntent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = intent.password)
            }
            is LoginIntent.SubmitLogin -> {
                performLogin(intent.isUser)
            }
        }
    }

    private fun performLogin(isUser: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            val result = authRepository.login(uiState.value.email, uiState.value.password, isUser)
            result.onSuccess {
                authToken ->
                _uiState.update { it.copy(isLoading = false, result = authToken.token ,message = authToken.message) }
                tokenStorage.saveToken(authToken.token)
            }
            result.onError { error->
            _uiState.update { it.copy(isLoading = false, errorMessage = error.toString()) }}
        }
    }

}
