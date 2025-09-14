package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.foodapp.data.dto.CheckUser
import com.foodapp.foodapp.data.dto.RestaurantAuthRequest
import com.foodapp.foodapp.data.dto.UserAuthRequest
import com.foodapp.foodapp.sharedObjects.SharedObject.baseUrl
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.HttpHeaders

class AuthApi(private val client: HttpClient,private val tokenStorage: TokenStorage) {
//192.168.214.37
    private val BASE_URL = baseUrl

    suspend fun loginUser(request: UserAuthRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("${BASE_URL}/login/user") {
                setBody(request)
            }
        }
    }
    suspend fun validate(): Result<CheckUser, DataError.Remote> {
        return safeCall<CheckUser> {
            client.get("$BASE_URL/user/validate"){
                headers{
                    append(HttpHeaders.Authorization,"Bearer ${tokenStorage.getToken().toString()}")
                }
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
