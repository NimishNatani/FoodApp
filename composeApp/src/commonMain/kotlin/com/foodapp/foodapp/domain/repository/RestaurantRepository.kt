package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User

interface RestaurantRepository {

    suspend fun getRestaurantByJwttoken(): Result<Restaurant, DataError.Remote>

}