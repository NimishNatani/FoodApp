package com.foodapp.foodapp.location

import com.foodapp.foodapp.presentation.location.LocationInterface
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse


class DesktopLocationClass : LocationInterface {
    override fun getLatAndLong(): Pair<Double, Double> {
        return Pair(0.0, 0.0)
    }

    override fun getCity(): String {
        return "Jaipur"
    }

    override fun getState(): String {
        return "Rajasthan"
    }

    override suspend fun geoLocation(httpClient: HttpClient): String {

        val apiUrl = "http://ip-api.com/json/"
        return try {
            val response: GeolocationResponse  = httpClient.get(apiUrl).body()
            if (response.status == "success") response.city else "Jaipur"
        } catch (e: Exception) {
            e.printStackTrace()
            "Jaipur"
        }
    }
}


@Serializable
data class GeolocationResponse(
    @SerialName("city") val city: String,
    @SerialName("regionName") val region: String,
    @SerialName("country") val country: String,
    @SerialName("status") val status: String
)