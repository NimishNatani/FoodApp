package com.foodapp.foodapp.storage

import android.content.Context

class AndroidTokenStorage(private val context: Context) : TokenStorage {
    private val prefs by lazy {
        context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
    }

    override  fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    override  fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    override  fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }

    override  fun isTokenAvailable(): Boolean {
        return prefs.contains("jwt_token")
    }
}


// AndroidMain source set


