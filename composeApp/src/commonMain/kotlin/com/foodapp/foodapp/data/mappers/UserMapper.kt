package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.UserDto
import com.foodapp.foodapp.domain.models.User

fun UserDto.toUser(): User {
    return User(
        userId = userId,
        username = username,
        userImage = userImage?:"",
        contactDetails = contactDetails?:"",
        bookingIds = bookingIds?: emptyList(),
        paymentIds = paymentIds?: emptyList(),
        reviewIds = reviewIds?: emptyList()
    )
}