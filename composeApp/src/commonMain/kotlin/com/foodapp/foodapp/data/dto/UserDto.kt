package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val username:String,
    val userImage:String?,
    val userId:String,
    val contactDetails:String?,
    val bookingIds:List<String>? =emptyList(),
    val paymentIds:List<String>? = emptyList(),
    val reviewIds:List<String>? = emptyList()
)
