package com.foodapp.foodapp.storage

import java.util.prefs.Preferences

class DesktopTokenStorage : TokenStorage {
    private val preferences: Preferences = Preferences.userRoot().node("your_app_token_storage")

    override  fun saveToken(token: String) {
        preferences.put("jwt_token", token)
    }

    override  fun getToken(): String? {
        return preferences.get("jwt_token", null)
    }

    override  fun clearToken() {
        preferences.remove("jwt_token")
    }

    override  fun isTokenAvailable(): Boolean {
        return preferences.get("jwt_token", null) != null
    }
}
