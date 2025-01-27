package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingDto(
    val bookingId: String,
    val userId: String,
    val restaurantId: String,
    val acceptedByRestaurant: Boolean,
    val bookingCompleted: Boolean,
    val message: List<MessageDto>,
    val foodCarts: List<FoodCartDto>,
    val acceptedTime: Int?,
    val paymentDone: Boolean,
    val paymentId: String?,
    val reviewList: List<ReviewDto>,
    val amount: Double?
)
