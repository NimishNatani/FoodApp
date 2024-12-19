package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.AuthToken
import com.foodapp.foodapp.domain.models.ValidateUser


interface AuthRepository {
    suspend fun login(email: String, password: String, isUser: Boolean): Result<AuthToken, DataError.Remote>
    suspend fun register(name: String,email: String, password: String, isUser: Boolean): Result<AuthToken, DataError.Remote>
    suspend fun validateUser():Result<ValidateUser,DataError.Remote>
}