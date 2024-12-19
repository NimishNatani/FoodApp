package com.foodapp.foodapp.domain.models

data class AuthToken(
    val token: String,
    val message:String
)

data class ValidateUser(
    val isUser: Boolean,
)
