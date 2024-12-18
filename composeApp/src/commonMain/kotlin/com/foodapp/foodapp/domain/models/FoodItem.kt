package com.foodapp.foodapp.domain.models

data class FoodItem(
    val foodId:String,
    val foodName:String,
    val quantity:Int,
    val singleFoodPrice:Double,
    val totalPrice:Double
)
