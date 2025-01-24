package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.RestaurantDto
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RestaurantApi(private val client: HttpClient, private val tokenStorage: TokenStorage) {

    private val BASE_URL = "http://10.14.6.239:8080/api"

    suspend fun getRestaurantByJwt(): Result<RestaurantDto, DataError.Remote> {
        return safeCall<RestaurantDto> {
            client.get("$BASE_URL/restaurant/profile") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${tokenStorage.getToken().toString()}"
                    )
                }
            }
        }
    }

    suspend fun getRestaurantsByCity(city:String): Result<List<RestaurantDto>, DataError.Remote> {
        return safeCall<List<RestaurantDto>> {
            client.get("$BASE_URL/user/restaurant/search/byCity") {
                headers {
                    append(
                        HttpHeaders.Authorization,
                        "Bearer ${tokenStorage.getToken().toString()}"
                    )
                }
                parameter("city", city)
            }
        }
    }

}