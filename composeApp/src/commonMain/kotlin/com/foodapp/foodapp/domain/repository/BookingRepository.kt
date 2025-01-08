package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.Booking

interface BookingRepository {

    suspend fun saveOrder(booking: Booking): Result<String, DataError.Remote>
}