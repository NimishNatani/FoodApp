package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.foodapp.data.api.RestaurantApi
import com.foodapp.foodapp.data.mappers.toRestaurant
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(private  val restaurantApi: RestaurantApi):RestaurantRepository {
    override suspend fun getRestaurantByJwttoken(): Result<Restaurant, DataError.Remote> {
        return  restaurantApi.getRestaurantByJwt().map { it.toRestaurant() }
    }
}