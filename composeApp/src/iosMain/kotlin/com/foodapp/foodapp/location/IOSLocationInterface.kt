package com.foodapp.foodapp.location

import android.util.Log
import com.foodapp.foodapp.domain.models.LocationData
import com.foodapp.foodapp.presentation.location.LocationInterface
import dev.jordond.compass.geocoder.MobileGeocoder
import dev.jordond.compass.geocoder.placeOrNull
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.mobile
import io.ktor.client.HttpClient

class IOSLocationInterface : LocationInterface {
    private val geoLocator = Geolocator.mobile()
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
        return locationData!!.postalCode
    }

    override suspend fun getLatAndLong(httpClient: HttpClient): Pair<Double, Double> {
        ensureLocationInitialized(httpClient)
        return Pair(locationData!!.latitude, locationData!!.longitude)
    }

    override suspend fun geoLocation(httpClient: HttpClient) {
        when (val result = geoLocator.current()) {
            is GeolocatorResult.Success -> {
                val coordinates = result.data.coordinates
                val place = MobileGeocoder().placeOrNull(coordinates)

                locationData = LocationData(
                    city = place?.locality ?: "Jaipur",
                    state = place?.administrativeArea ?: "Rajasthan",
                    country = place?.country ?: "India",
                    postalCode = place?.postalCode ?: "302001",
                    latitude = coordinates.latitude,
                    longitude = coordinates.longitude
                )
                println(place)
            }

            is GeolocatorResult.Error -> {
                Log.d("Location", "LOCATION ERROR: ${result.message}")
                locationData = LocationData( // fallback values
                    city = "Jaipur",
                    state = "Rajasthan",
                    country = "India",
                    postalCode = "302001",
                    latitude = 26.9124,
                    longitude = 75.7873
                )
            }

            else -> {
                locationData = LocationData( // fallback
                    city = "Jaipur",
                    state = "Rajasthan",
                    country = "India",
                    postalCode = "302001",
                    latitude = 26.9124,
                    longitude = 75.7873
                )
            }
        }
    }

    private suspend fun ensureLocationInitialized(httpClient: HttpClient) {
        if (locationData == null) {
            geoLocation(httpClient)
        }
    }
}
