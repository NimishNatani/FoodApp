package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthRequest(
     val username: String,
     val userId: String,
     val password: String
)
@Serializable
data class RestaurantAuthRequest(
     val restaurantId: String,
     val password: String
)

@Serializable
data class AuthResponse(
     val jwt: String,    // Matches the "jwt" field in the JSON
     val message: String //)
)
@Serializable
data class CheckUser(
     val userType: String,
)