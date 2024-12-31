package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.FoodCartDetailDto
import com.foodapp.foodapp.data.dto.FoodCartDto
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.models.FoodCartDetail

fun FoodCartDto.toFoodCart():FoodCart{
    return FoodCart(
        foodId = foodId,
        foodName = foodName,
        foodImage = foodImage,
        foodTags = foodTags,
        foodDescription = foodDescription,
        foodCartDetailsList = foodCartDetailsList.map {  it.toFoodCartDetail() },
        totalPrice = totalPrice?:0.0,
        restaurantId = restaurantId,
        restaurantName = restaurantName
    )
}

fun FoodCartDetailDto.toFoodCartDetail(): FoodCartDetail {
    return   FoodCartDetail(
        foodSize = foodSize,
        foodPrice = foodPrice?:0.0,
        quantity = quantity?:0
    )

}