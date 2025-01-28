package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.models.User

interface UserRepository {

    suspend fun getUserByJwttoken():Result<User,DataError.Remote>

    suspend fun addItemToCart(foodCart: FoodCart):Result<String,DataError.Remote>

    suspend fun deleteItemFromCart(foodCartList: List<FoodCart>):Result<String,DataError.Remote>

    suspend fun getFoodCart():Result<List<FoodCart>,DataError.Remote>
}