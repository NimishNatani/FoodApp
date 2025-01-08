package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    var bookingId: String,
    var userId: String,
    var restaurantId: String,
    var isAcceptedByRestaurant: Boolean,
    var isBookingCompleted: Boolean,
    var message: List<Message>,
    var foodCarts: List<FoodCart>,
    var acceptedTime: Int?,
    var isPaymentDone: Boolean,
    var paymentId: String?,
    var reviewList: List<Review>,
    var amount: Double?
)