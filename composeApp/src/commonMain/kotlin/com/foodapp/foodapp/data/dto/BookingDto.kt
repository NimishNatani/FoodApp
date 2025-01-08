package com.foodapp.foodapp.data.dto

data class BookingDto(
    val bookingId: String,
    val userId: String,
    val restaurantId: String,
    val isAcceptedByRestaurant: Boolean,
    val isBookingCompleted: Boolean,
    val message: List<MessageDto>,
    val foodCarts: List<FoodCartDto>,
    val acceptedTime: Int?,
    val isPaymentDone: Boolean,
    val paymentId: String?,
    val reviewList: List<ReviewDto>,
    val amount: Double?
)
