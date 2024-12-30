package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FoodCart(
    val foodId:String,
    val foodName:String,
    val foodImage:String,
    val foodTags : List<String>,
    val foodDescription : String,
    val foodCartDetailsList: List<FoodCartDetail>,
    val totalPrice:Double=0.0,
    val restaurantId:String,
    val restaurantName:String
)


@Serializable
data class FoodCartDetail(
    var foodSize: String,
    var foodPrice :Double,
    var quantity :Int=0
)
