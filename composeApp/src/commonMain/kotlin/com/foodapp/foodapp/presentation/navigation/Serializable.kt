package com.foodapp.foodapp.presentation.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.eygraber.uri.UriCodec
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val UserSerializable = object : NavType<User>(
        isNullableAllowed = false,
    ) {
        override fun get(bundle: Bundle, key: String): User? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): User {
            return Json.decodeFromString(UriCodec.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: User) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: User): String {
            return UriCodec.encode(Json.encodeToString(value))
        }
    }

    val RestaurantSerializable = object : NavType<Restaurant>(
        isNullableAllowed = false,
    ) {
        override fun get(bundle: Bundle, key: String): Restaurant? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Restaurant {
            return Json.decodeFromString(UriCodec.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Restaurant) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Restaurant): String {
            return UriCodec.encode(Json.encodeToString(value))
        }
    }
}