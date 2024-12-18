package com.foodapp.foodapp.domain.models

data class Message(
    val sender: String,
    val content: String,
    val createdTimestamp: String =""
)