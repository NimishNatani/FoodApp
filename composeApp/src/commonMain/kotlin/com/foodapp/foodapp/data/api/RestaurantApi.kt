package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.RestaurantDto
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

class RestaurantApi(private val client: HttpClient, private val tokenStorage: TokenStorage) {

    private val BASE_URL = "http://10.14.2.122:8080/api"

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
            try {
                client.get("$BASE_URL/user/restaurant/search/byCity") {
                    headers {
                        append(
                            HttpHeaders.Authorization,
                            "Bearer ${tokenStorage.getToken().toString()}"
                        )
                    }
                    parameter("city", city)
                }
            } catch (e: Exception) {
                println("Error occurred: ${e.message}")
                throw e
            }
        }
    }

    suspend fun addRestaurantData(restaurant: Restaurant): Result<String, DataError.Remote> {
        return safeCall<String> {
            client.post("$BASE_URL/restaurant/add") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${tokenStorage.getToken()}")
                }
                setBody(restaurant) // JSON body
            }
        }
    }

    suspend fun uploadRestaurantImage(
        restaurantId: String,
        imageBytes: ByteArray,
        type:String
    ): Result<String, DataError.Remote> {
        return safeCall<String> {
            client.submitFormWithBinaryData(
                url = "$BASE_URL/restaurant/uploadImage",
                formData = formData {
                    append("restaurantId", restaurantId)
                    append("type", type)
                    append("image", imageBytes, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(HttpHeaders.ContentDisposition, "filename=${restaurantId}.jpg")
                    })
                }
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${tokenStorage.getToken()}")
                }
            }
        }
    }

}