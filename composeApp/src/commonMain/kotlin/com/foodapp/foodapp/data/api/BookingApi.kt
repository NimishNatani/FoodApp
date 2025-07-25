package com.foodapp.foodapp.data.api

import com.foodapp.core.di.safeCall
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.dto.BookingDto
import com.foodapp.foodapp.data.dto.RestaurantDto
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class BookingApi(private val client: HttpClient, private val tokenStorage: TokenStorage) {

    private val BASE_URL = "http://10.38.139.37:8080/api"

    suspend fun saveOrder(bookingDetails: Booking): Result<String, DataError.Remote> {
        println("booking : $bookingDetails")
        return safeCall<String> {
            try {
                client.put("$BASE_URL/booking/book") {
                    headers {
                        append(
                            HttpHeaders.Authorization,
                            "Bearer ${tokenStorage.getToken().toString()}"
                        )
                    }
                    setBody(bookingDetails)
                }
            } catch (e: Exception) {
                println("Error occurred: ${e.message}")
                throw e
            }
        }
    }

    suspend fun getOrders():Result<List<BookingDto>,DataError.Remote>{
        return safeCall<List<BookingDto>> {
            try {
                client.get("$BASE_URL/booking/userOrders"){
                    headers {
                        append(
                            HttpHeaders.Authorization,
                            "Bearer ${tokenStorage.getToken().toString()}"
                        )
                    }
                }
            } catch (e: Exception) {
                println("Error occurred: ${e.message}")
                throw e
            }
        }
    }
}