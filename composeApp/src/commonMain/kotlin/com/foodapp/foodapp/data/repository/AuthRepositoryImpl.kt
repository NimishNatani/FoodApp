package com.foodapp.foodapp.data.repository

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.foodapp.data.api.AuthApi
import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.foodapp.data.mappers.toAuthToken
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.foodapp.data.dto.RestaurantAuthRequest
import com.foodapp.foodapp.data.dto.UserAuthRequest
import com.foodapp.foodapp.data.mappers.toValidateUser
import com.foodapp.foodapp.domain.models.AuthToken
import com.foodapp.foodapp.domain.models.ValidateUser
import com.foodapp.foodapp.storage.TokenStorage


class AuthRepositoryImpl(
    private val apiService: AuthApi,
) : AuthRepository {

    override suspend fun login(email: String, password: String, isUser: Boolean): Result<AuthToken, DataError.Remote> {

        return if (isUser) {
            val request = UserAuthRequest("",email, password)
            apiService.loginUser(request).map { it.toAuthToken() }
        } else {val request = RestaurantAuthRequest("",email, password)
//            apiService.validate().map { it.toAuthToken() }
            apiService.loginRestaurant(request).map { it.toAuthToken() }
        }
    }

    override suspend fun register(name:String,email: String, password: String, isUser: Boolean): Result<AuthToken, DataError.Remote> {
        return if (isUser){
            val request = UserAuthRequest(name,email, password)
            apiService.registerUser(request).map { it.toAuthToken() }
        } else {val request = RestaurantAuthRequest(name,email, password)
            apiService.registerRestaurant(request).map { it.toAuthToken() } }
    }

    override suspend fun validateUser(): Result<ValidateUser, DataError.Remote> {
        return apiService.validate().map { it.toValidateUser() };
    }
}
