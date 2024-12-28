package com.foodapp.foodapp.presentation.navigation

import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed interface Route {

    @Serializable
    data object AuthGraph:Route

    @Serializable
    data object UserGraph : Route

    @Serializable
    data object RestaurantGraph : Route

    @Serializable
    data object SplashScreen : Route

    @Serializable
    data object UserSelection : Route

    @Serializable
    data object Register : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object UserHomeScreen : Route

    @Serializable
    data object RestaurantHomeScreen : Route

    @Serializable
    data object ViewAllRestaurantScreen : Route
}
