package com.foodapp.foodapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object SplashScreen : Route

    @Serializable
    data object UserSelection : Route

    @Serializable
    data object Register : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object UserHomeScreen:Route

    @Serializable
    data object RestaurantHomeScreen:Route
}
