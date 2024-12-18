package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.foodapp.data.dto.RestaurantAuthRequest
import com.foodapp.foodapp.data.dto.UserAuthRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AuthApi(private val client: HttpClient) {

    private val BASE_URL = "http://localhost:8080/api"

    suspend fun loginUser(request: UserAuthRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("${BASE_URL}/login/user") {
                setBody(request)
            }
        }
    }

    suspend fun registerUser(request: UserAuthRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("$BASE_URL/register/user") {
                setBody(request)
            }
        }
    }

    suspend fun loginRestaurant(request: RestaurantAuthRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("$BASE_URL/login/restaurant") {
                setBody(request)
            }
        }
    }

    suspend fun registerRestaurant(request: RestaurantAuthRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("$BASE_URL/register/restaurant") {
                setBody(request)
            }
        }
    }

}
