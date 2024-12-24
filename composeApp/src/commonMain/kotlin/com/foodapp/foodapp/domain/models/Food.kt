package com.foodapp.foodapp.domain.models

data class Food(
    var foodId: String,
    var foodName: String,
    var foodDescription: String,
    var foodImage: String,
    var foodDetails: List<FoodDetails>,
    var isAvailable: Boolean,
    var isVeg: Boolean,
    var rating: Double?,
    var totalReviews: Int?,
    var restaurantId: String
)

data class FoodDetails(
    var foodSize: String,
    var foodPrice :Double
)