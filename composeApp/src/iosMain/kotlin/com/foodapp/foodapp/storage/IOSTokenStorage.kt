package com.foodapp.foodapp.storage

import platform.Foundation.NSUserDefaults

class IOSTokenStorage : TokenStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override  fun saveToken(token: String) {
        defaults.setObject(token, "jwt_token")
        defaults.synchronize()
    }

    override  fun getToken(): String? {
        return defaults.stringForKey("jwt_token")
    }

    override  fun clearToken() {
        defaults.removeObjectForKey("jwt_token")
        defaults.synchronize()
    }

    override  fun isTokenAvailable(): Boolean {
        return defaults.stringForKey("jwt_token") != null
    }
}
