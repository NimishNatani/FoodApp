package com.foodapp.foodapp.domain.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.foodapp.domain.models.User

interface UserRepository {

    suspend fun getUserByJwttoken():Result<User,DataError.Remote>
}