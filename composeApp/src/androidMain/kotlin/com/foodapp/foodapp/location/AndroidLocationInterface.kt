package com.foodapp.foodapp.location

import android.util.Log
import com.foodapp.foodapp.presentation.location.LocationInterface
import dev.jordond.compass.geocoder.MobileGeocoder
import dev.jordond.compass.geocoder.placeOrNull
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import io.ktor.client.HttpClient

class AndroidLocationInterface : LocationInterface {
    private val geoLocation = Geolocator.mobile()

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
        return when (val result = geoLocation.current()) {
            is GeolocatorResult.Success -> {
                val coordinates = result.data.coordinates
                val locality = MobileGeocoder().placeOrNull(coordinates)?.locality
                if (!locality.isNullOrEmpty()) {
                    locality // Return the actual city name
                } else {
                    "Jaipur" // Fallback if geocoder fails
                }
            }

            is GeolocatorResult.Error -> {
                Log.d("Location","LOCATION ERROR: ${result.message}")
                when (result) {
                    is GeolocatorResult.PermissionError -> {
                        println("Please enable location permissions.")
                    }

                    is GeolocatorResult.NotFound -> {
                        println("No location found.")
                    }

                    is GeolocatorResult.GeolocationFailed -> {
                        println("Geolocation failed.")
                    }

                    else -> {
                        println("Unsupported geolocation error.")
                    }
                }
                "Jaipur" // Fallback for errors
            }

            else -> "Jaipur"
        }
    }
}
