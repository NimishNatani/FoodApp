package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    var restaurantId: String,
    var restaurantImage: String="",
    var restaurantName: String,
    var contactDetails: String="",
    var latitude: Double,
    var longitude: Double,
    var address: String,
    var city: String,
    var state: String,
    var postalCode: String,
    var totalReviews: Int = 0,
    var ratings: Double? = null,
    var bookingIds: List<String> = emptyList(),
    var paymentIds: List<String> = emptyList(),
    var foodItems: List<String> = emptyList(),
    var reviewIds: List<String> = emptyList()
)