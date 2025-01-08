package com.foodapp.foodapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val sender: String,
    val content: String,
    val createdTimestamp: String?
)