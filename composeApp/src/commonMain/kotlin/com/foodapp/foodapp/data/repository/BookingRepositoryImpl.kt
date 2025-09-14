package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.foodapp.data.api.BookingApi
import com.foodapp.foodapp.data.mappers.toBooking
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.repository.BookingRepository

class BookingRepositoryImpl(private val apiService: BookingApi) : BookingRepository {
    override suspend fun saveOrder(booking: Booking): Result<String, DataError.Remote> {
        println("booking : $booking")
        return apiService.saveOrder(booking);
    }

    override suspend fun getOrders(): Result<List<Booking>,DataError.Remote>  {
        val apiResponse = apiService.getOrders()
        return apiResponse.map { dto -> dto.map { it.toBooking() } }

    }

    override suspend fun getOrder(userId: String): List<Booking> {
        val sample = mutableListOf<Booking>(
            Booking(
                bookingId = "101",
                userId = "u1",
                restaurantId = "r1",
                isAcceptedByRestaurant = false,
                isBookingCompleted = false,
                message = emptyList(),
                foodCarts = emptyList(),
                acceptedTime = null,
                isPaymentDone = true,
                paymentId = "p1",
                reviewList = emptyList(),
                amount = 420.0
            ),
            Booking(
                bookingId = "102",
                userId = "u2",
                restaurantId = "r1",
                isAcceptedByRestaurant = true,
                isBookingCompleted = false,
                message = emptyList(),
                foodCarts = emptyList(),
                acceptedTime = 1680000000,
                isPaymentDone = true,
                paymentId = "p2",
                reviewList = emptyList(),
                amount = 520.0
            ),
            Booking(
                bookingId = "103",
                userId = "u3",
                restaurantId = "r1",
                isAcceptedByRestaurant = true,
                isBookingCompleted = true,
                message = emptyList(),
                foodCarts = emptyList(),
                acceptedTime = 1670000000,
                isPaymentDone = true,
                paymentId = "p3",
                reviewList = emptyList(),
                amount = 300.0
            )
        )
        return sample.toList()
    }


}