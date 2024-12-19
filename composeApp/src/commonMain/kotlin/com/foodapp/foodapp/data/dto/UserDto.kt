package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val username:String,
    val userImage:String,
    val userId:String,
    val contactDetails:String,
    val bookingIds:List<String>,
    val paymentIds:List<String>,
    val reviewIds:List<String>
)
