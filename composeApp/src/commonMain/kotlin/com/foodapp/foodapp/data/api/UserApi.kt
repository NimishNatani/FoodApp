package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.UserDto
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders

class UserApi(private val client: HttpClient, private val tokenStorage: TokenStorage) {
    private val BASE_URL = "http://localhost:8080/api"


    suspend fun getUserByJwt(): Result<UserDto, DataError.Remote> {
        return safeCall<UserDto> {
            client.post("$BASE_URL/user/profile") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${tokenStorage.getToken().toString()}"
                    )
                }
            }
        }
    }

}