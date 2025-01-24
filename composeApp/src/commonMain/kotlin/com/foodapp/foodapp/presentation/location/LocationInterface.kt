package com.foodapp.foodapp.presentation.location

import io.ktor.client.HttpClient

interface LocationInterface {
    fun getLatAndLong(): Pair<Double, Double>
    fun getCity(): String
    fun getState():String
    suspend fun geoLocation(httpClient: HttpClient):String
}