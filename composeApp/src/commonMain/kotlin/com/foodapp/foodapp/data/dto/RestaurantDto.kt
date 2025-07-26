package com.foodapp.foodapp.data.dto

import com.foodapp.foodapp.domain.models.Food
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    val restaurantId: String?,
    val restaurantImage: String?,
    val restaurantName: String?,
    val contactDetails: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val city: String?,
    val state: String?,
    val postelCode: String?,
    val totalReviews: Int,
    val ratings: Double?=null,
    val restaurantTags: List<String>?=emptyList(),
    val bookingIds: List<String>? =emptyList(),
    val paymentIds: List<String>? =emptyList(),
    val foodItems: List<FoodDto>? =emptyList(),
    val reviewIds: List<String>? =emptyList()
)
