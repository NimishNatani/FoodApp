package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.FoodCartDto
import com.foodapp.foodapp.data.dto.UserDto
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class UserApi(private val client: HttpClient, private val tokenStorage: TokenStorage) {
    private val BASE_URL = "http://192.168.214.37:8080/api"


    suspend fun getUserByJwt(): Result<UserDto, DataError.Remote> {
        return safeCall<UserDto> {
            client.get("$BASE_URL/user/profile") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${tokenStorage.getToken().toString()}"
                    )
                }
            }
        }
    }

    suspend fun addItemToCart(foodCart: FoodCart): Result<String, DataError.Remote> {
        return safeCall<String> {
            client.put("$BASE_URL/user/addItemToCart") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${tokenStorage.getToken().toString()}"
                    )
                }
                setBody(foodCart)
            }
        }
    }

    suspend fun getFoodCart(): Result<List<FoodCartDto>, DataError.Remote> {
        return safeCall<List<FoodCartDto>> {
            client.get("$BASE_URL/user/getItemFromCart") {
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