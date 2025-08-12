package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.RestaurantDto
import com.foodapp.foodapp.domain.models.Restaurant

fun RestaurantDto.toRestaurant():Restaurant {
   return Restaurant(
        restaurantId = restaurantId,
        restaurantImage = restaurantImage!!,
        restaurantName = restaurantName!!,
        contactDetails = contactDetails!!,
        latitude = latitude,
        longitude = longitude,
        address = address,
        city = city,
        state = state,
        postelCode = postelCode,
        totalReviews = totalReviews,
        ratings = ratings,
        bookingIds = bookingIds?: emptyList(),
        paymentIds = paymentIds?: emptyList(),
        foodItems =foodItems!!.map { food ->
             food.toFood()
        },
        restaurantTags = restaurantTags?: emptyList(),
        reviewIds = reviewIds?: emptyList()
    )
}