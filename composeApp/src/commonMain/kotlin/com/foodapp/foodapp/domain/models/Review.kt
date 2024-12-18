package com.foodapp.foodapp.domain.models

data class Review(
    val reviewId: String,
    val message: String,
    val ratings: Double,
    val restaurantId: String,
    val userId: String,
    val reviewTime: String
)