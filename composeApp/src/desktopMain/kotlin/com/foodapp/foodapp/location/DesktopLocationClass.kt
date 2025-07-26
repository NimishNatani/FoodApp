package com.foodapp.foodapp.location

import com.foodapp.foodapp.domain.models.LocationData
import com.foodapp.foodapp.presentation.location.LocationInterface
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DesktopLocationClass : LocationInterface {
    private var locationData: LocationData? = null

    override suspend fun getCity(httpClient: HttpClient): String {
        ensureLocationInitialized(httpClient)
        return locationData!!.city
    }

    override suspend fun getState(httpClient: HttpClient): String {
        ensureLocationInitialized(httpClient)
        return locationData!!.state
    }

    override suspend fun getCountry(httpClient: HttpClient): String {
        ensureLocationInitialized(httpClient)
        return locationData!!.country
    }

    override suspend fun getPostalCode(httpClient: HttpClient): String {
        ensureLocationInitialized(httpClient)
        return locationData!!.postalCode // fallback only
    }

    override suspend fun getLatAndLong(httpClient: HttpClient): Pair<Double, Double> {
        ensureLocationInitialized(httpClient)
        return Pair(locationData!!.latitude, locationData!!.longitude)
    }

     private suspend fun geoLocation(httpClient: HttpClient) {
        val apiUrl = "http://ip-api.com/json/"
        locationData = try {
            val response: GeolocationResponse = httpClient.get(apiUrl).body()
            if (response.status == "success") {
                LocationData(
                    city = response.city,
                    state = response.region,
                    country = response.country,
                    postalCode = "000000", // No postal from ip-api, use fallback
                    latitude = response.lat,
                    longitude = response.lon
                )
            } else {
                fallbackLocation()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fallbackLocation()
        }
    }

    private fun fallbackLocation(): LocationData = LocationData(
        city = "Ajmer",
        state = "Rajasthan",
        country = "India",
        postalCode = "305001",
        latitude = 26.4499,
        longitude = 74.6399
    )

    private suspend fun ensureLocationInitialized(httpClient: HttpClient) {
        if (locationData == null) {
            geoLocation(httpClient)
        }
    }
}

@Serializable
data class GeolocationResponse(
    @SerialName("city") val city: String,
    @SerialName("regionName") val region: String,
    @SerialName("country") val country: String,
    @SerialName("status") val status: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double
)
