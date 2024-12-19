package com.foodapp.foodapp.presentation.navigation

import com.foodapp.foodapp.domain.models.User
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object FoodGraph:Route

    @Serializable
    data object SplashScreen : Route

    @Serializable
    data object UserSelection : Route

    @Serializable
    data object Register : Route

    @Serializable
    data object Login : Route

    @Serializable
    data class UserHomeScreen(val user: User):Route

    @Serializable
    data object RestaurantHomeScreen:Route
}
