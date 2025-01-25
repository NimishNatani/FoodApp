package com.foodapp.foodapp.data.dto

data class ReviewDto(
    val reviewId: String,
    val userId: String,
    val restaurantId: String,
    val ratings: Double?,
    val message: String,
    val reviewTime: String
)
