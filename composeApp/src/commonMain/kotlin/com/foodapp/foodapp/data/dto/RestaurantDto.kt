package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantDto(
    val restaurantId: String,
    val restaurantImage: String,
    val restaurantName: String,
    val contactDetails: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val totalReviews: Int,
    val ratings: Double,
    val bookingIds: List<String>,
    val paymentIds: List<String>,
    val foodItems: List<String>,
    val reviewIds: List<String>
)
