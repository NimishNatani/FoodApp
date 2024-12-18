package com.foodapp.foodapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object SplashScreen : Route

    @Serializable
    data object UserSelection : Route

    @Serializable
    data class Register(val isUser: Boolean) : Route{
        companion object {
            const val route = "register/{isUser}"
            fun createRoute(isUser: Boolean) = "register/$isUser"
        }
    }

    @Serializable
    data class Login(val isUser: Boolean) : Route {
        companion object {
            const val route = "login/{isUser}"
            fun createRoute(isUser: Boolean) = "login/$isUser"
        }
    }
}
