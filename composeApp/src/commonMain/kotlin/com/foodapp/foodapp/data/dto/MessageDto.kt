package com.foodapp.foodapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val sender : String,
    val content : String,
    val createdTimestamp : String?
)
