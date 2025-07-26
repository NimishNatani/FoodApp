package com.foodapp.foodapp.presentation.location

import io.ktor.client.HttpClient

interface LocationInterface {
    suspend fun getLatAndLong(httpClient: HttpClient): Pair<Double, Double>
    suspend fun getCity(httpClient: HttpClient): String
    suspend fun getState(httpClient: HttpClient):String
    suspend fun getCountry(httpClient: HttpClient):String
    suspend fun getPostalCode(httpClient: HttpClient):String
//    suspend fun geoLocation():String
}