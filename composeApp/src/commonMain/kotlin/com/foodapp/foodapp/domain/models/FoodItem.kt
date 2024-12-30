package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

data class FoodItem(
    val foodId:String,
    val foodName:String,
    val foodImage:String,
    val foodTags : List<String>,
    val foodDescription : String,
    val foodItemDetails: List<FoodItemDetails>,
    val totalPrice:Double=0.0,
    val restaurantId:String
)


@Serializable
data class FoodItemDetails(
    var foodSize: String,
    var foodPrice :Double,
    var quantity :Int=0
)
