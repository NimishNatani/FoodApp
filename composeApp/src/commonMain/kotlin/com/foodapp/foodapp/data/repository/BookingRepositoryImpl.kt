package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.data.api.BookingApi
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.repository.BookingRepository

class BookingRepositoryImpl(private val apiService: BookingApi) : BookingRepository {
    override suspend fun saveOrder(booking: Booking): Result<String, DataError.Remote> {
        println("booking : $booking")
        return apiService.saveOrder(booking);
    }
}