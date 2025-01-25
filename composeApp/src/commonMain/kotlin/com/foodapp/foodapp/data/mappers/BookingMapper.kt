package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.BookingDto
import com.foodapp.foodapp.data.dto.MessageDto
import com.foodapp.foodapp.data.dto.ReviewDto
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.models.Message
import com.foodapp.foodapp.domain.models.Review

fun BookingDto.toBooking(): Booking {
    return Booking(
        bookingId = bookingId,
        userId = userId,
        restaurantId = restaurantId,
        isAcceptedByRestaurant = isAcceptedByRestaurant,
        isBookingCompleted = isBookingCompleted,
        message = message.map { it.toMessage() },
        foodCarts = foodCarts.map { it.toFoodCart() },
        acceptedTime = acceptedTime,
        isPaymentDone = isPaymentDone,
        paymentId = paymentId,
        reviewList = reviewList.map { it.toReview() },
        amount = amount
    )
}

fun MessageDto.toMessage():Message{
    return Message(
        sender = sender,
        content = content,
        createdTimestamp = timeStamp

    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        reviewId = reviewId,
        message = message,
        ratings = ratings,
        userId = userId,
        restaurantId = restaurantId,
        reviewTime = reviewTime

    )
}