package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.RestaurantDto
import com.foodapp.foodapp.domain.models.Restaurant

fun RestaurantDto.toRestaurant():Restaurant {
   return Restaurant(
        restaurantId = restaurantId,
        restaurantImage = restaurantImage,
        restaurantName = restaurantName,
        contactDetails = contactDetails,
        latitude = latitude,
        longitude = longitude,
        address = address,
        city = city,
        state = state,
        postalCode = postelCode,
        totalReviews = totalReviews,
        ratings = ratings,
        bookingIds = bookingIds,
        paymentIds = paymentIds,
        foodItems =foodItems.map { food ->
             food.toFood()
        },
        reviewIds = reviewIds
    )
}