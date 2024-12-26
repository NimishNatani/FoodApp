package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    val foodId: String,
    val foodName: String,
    val foodDescription: String,
    val foodTags: List<String>,
    val foodImage: String,
    val foodDetailList: List<FoodDetailsDto>,
    val available: Boolean,
    val veg: Boolean,
    val rating: Double?,
    val totalReviews: Int?,
    val restaurantId: String
)

@Serializable
data class FoodDetailsDto(
    val foodSize: String,
    val foodPrice: Double
)