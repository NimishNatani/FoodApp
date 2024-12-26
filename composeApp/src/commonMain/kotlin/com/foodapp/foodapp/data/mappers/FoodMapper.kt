package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.FoodDetailsDto
import com.foodapp.foodapp.data.dto.FoodDto
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodDetails

fun FoodDto.toFood(): Food {
    return Food(
        foodId = foodId,
        foodName = foodName,
        foodDescription = foodDescription,
        foodImage = foodImage,
        foodTags = foodTags,
        foodDetails = foodDetailList.map { it.toFoodDetail() },
        isAvailable = available,
        isVeg = veg,
        rating = rating,
        totalReviews = totalReviews,
        restaurantId = restaurantId
    )
}

fun FoodDetailsDto.toFoodDetail(): FoodDetails {
    return FoodDetails(
        foodSize = foodSize,
        foodPrice = foodPrice
    )
}

