package com.foodapp.foodapp.data.repository

import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.map
import com.foodapp.foodapp.data.api.UserApi
import com.foodapp.foodapp.data.mappers.toUser
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi):UserRepository {
    override suspend fun getUserByJwttoken(): Result<User, DataError.Remote> {
        return userApi.getUserByJwt().map { it.toUser() }
    }
}