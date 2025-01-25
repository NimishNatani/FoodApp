package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val reviewId: String,
    val message: String,
    val ratings: Double?,
    val restaurantId: String,
    val userId: String,
    val reviewTime: String?
)