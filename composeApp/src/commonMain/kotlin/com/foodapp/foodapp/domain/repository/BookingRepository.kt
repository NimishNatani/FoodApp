package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.Booking

interface BookingRepository {

    suspend fun saveOrder(booking: Booking): Result<String, DataError.Remote>

    suspend fun getOrders():Result<List<Booking>, DataError.Remote>
    suspend fun getOrder( userId:String):List<Booking>

//    suspend fun updateOrder()
}