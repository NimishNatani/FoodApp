package com.foodapp.foodapp.presentation.starter

import androidx.lifecycle.ViewModel
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.ValidateUser
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.storage.TokenStorage

class AuthValidationViewModel(private val authRepository: AuthRepository):ViewModel() {

    suspend fun validateUser(): Result<ValidateUser, DataError.Remote> {
        return authRepository.validateUser()
    }
}