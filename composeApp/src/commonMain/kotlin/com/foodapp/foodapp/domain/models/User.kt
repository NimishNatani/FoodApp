package com.foodapp.foodapp.domain.models

data class User(
    val userId:String,
    val username:String,
    val userImage:String="",
    val contactDetails:String="",
    val bookingIds:List<String> =emptyList(),
    val paymentIds:List<String> = emptyList(),
    val reviewIds:List<String> = emptyList()
)

