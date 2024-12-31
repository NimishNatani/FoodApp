package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.data.api.UserApi
import com.foodapp.foodapp.data.mappers.toFoodCart
import com.foodapp.foodapp.data.mappers.toUser
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi):UserRepository {
    override suspend fun getUserByJwttoken(): Result<User, DataError.Remote> {
        return userApi.getUserByJwt().map { it.toUser() }
    }

    override suspend fun addItemToCart(foodCart: FoodCart): Result<String, DataError.Remote> {
       return userApi.addItemToCart(foodCart)
    }

    override suspend fun getFoodCart(): Result<List<FoodCart>, DataError.Remote> {
        val apiResponse = userApi.getFoodCart()
        apiResponse.onSuccess { println("food :$it") }
        apiResponse.onError { println("Error :$it") }
        return apiResponse.map { dto -> dto.map { it.toFoodCart() }}
    }
}