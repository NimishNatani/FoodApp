package com.foodapp.foodapp.presentation

data class SnackSharedElementKey(
    val snackId: String,
    val origin: String,
    val type: SnackSharedElementType
)

enum class SnackSharedElementType {
    Bounds,
    Image,
    Title,
    Tagline,
    FoodPrice,
    Icon,
    Distance,
    Rating,
    Address,
    Background
}