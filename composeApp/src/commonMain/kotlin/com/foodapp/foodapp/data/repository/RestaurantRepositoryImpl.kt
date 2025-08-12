package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.data.api.RestaurantApi
import com.foodapp.foodapp.data.mappers.toRestaurant
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(private  val restaurantApi: RestaurantApi):RestaurantRepository {

    override suspend fun getRestaurantByJwttoken(): Result<Restaurant, DataError.Remote> {
        return  restaurantApi.getRestaurantByJwt().map { it.toRestaurant() }
    }

    override suspend fun getRestaurantsByCity(city: String): Result<List<Restaurant>, DataError.Remote> {
        val apiResponse = restaurantApi.getRestaurantsByCity(city)
        apiResponse.onSuccess { println(it) }
        return apiResponse.map {dto ->dto.map { it.toRestaurant() } }
    }

    override suspend fun addRestaurant(
        restaurant: Restaurant
    ): Result<String, DataError.Remote> {
        return restaurantApi.addRestaurantData(restaurant)
    }

    override suspend fun uploadImage(image: ByteArray,restaurantId:String,type:String): Result<String, DataError.Remote> {
        return restaurantApi.uploadRestaurantImage(restaurantId,image,type)
    }
}