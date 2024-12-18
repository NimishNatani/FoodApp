package com.foodapp.foodapp.storage


interface TokenStorage {
     fun saveToken(token: String)
     fun getToken(): String?
     fun clearToken()
     fun isTokenAvailable(): Boolean
}