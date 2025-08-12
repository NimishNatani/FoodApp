package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User

interface RestaurantRepository {

    suspend fun getRestaurantByJwttoken(): Result<Restaurant, DataError.Remote>

    suspend fun getRestaurantsByCity(city: String):Result<List<Restaurant>, DataError.Remote>

    suspend fun addRestaurant(restaurant: Restaurant):Result<String, DataError.Remote>

    suspend fun uploadImage(image:ByteArray,restaurantId:String,type:String):Result<String, DataError.Remote>


}