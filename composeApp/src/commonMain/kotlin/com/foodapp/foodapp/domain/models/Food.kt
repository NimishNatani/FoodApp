package com.foodapp.foodapp.domain.models

import kotlinproject.composeapp.generated.resources.Res
import kotlinx.serialization.Serializable

@Serializable
data class Food(
    var foodId: String,
    var foodName: String,
    var foodDescription: String,
    var foodTags:List<String>,
    var foodImage: String,
    var foodDetails: List<FoodDetails>,
    var isAvailable: Boolean,
    var isVeg: Boolean,
    var foodType: List<String>,
    var rating: Double?,
    var totalReviews: Int?,
    var restaurantId: String
)

@Serializable
data class FoodDetails(
    var foodSize: String,
    var foodPrice :Double
)