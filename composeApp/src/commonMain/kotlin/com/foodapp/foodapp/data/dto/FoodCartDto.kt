package com.foodapp.foodapp.data.dto

import com.foodapp.foodapp.domain.models.FoodCartDetail
import kotlinx.serialization.Serializable

@Serializable
data class FoodCartDto(
    val foodId:String,
    val foodName:String,
    val foodImage:String,
    val foodTags : List<String>,
    val foodDescription : String,
    val foodItemDetails: List<FoodCartDetailDto>,
    val totalPrice:Double=0.0,
    val restaurantId:String,
    val restaurantName:String
)

@Serializable
data class FoodCartDetailDto(
    var foodSize: String,
    var foodPrice :Double,
    var quantity :Int=0
)
