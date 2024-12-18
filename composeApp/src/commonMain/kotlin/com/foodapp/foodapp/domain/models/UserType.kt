package com.foodapp.foodapp.domain.models

sealed class UserType(val apiKey: String) {
    data object RegularUser : UserType("user")
    data object Restaurant : UserType("restaurant")
}