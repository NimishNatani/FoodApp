package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val reviewId: String,
    val userId: String,
    val restaurantId: String,
    val ratings: Double?,
    val message: String,
    val reviewTime: String
)
