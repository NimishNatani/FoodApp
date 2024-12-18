package com.foodapp.foodapp.domain.models

data class User(
    val userId:String,
    val username:String,
    val userImage:String,
    val contactDetails:String,
    val password:String,
    val bookingIds:List<String>,
    val paymentIds:List<String>,
    val reviewIds:List<String>
)

