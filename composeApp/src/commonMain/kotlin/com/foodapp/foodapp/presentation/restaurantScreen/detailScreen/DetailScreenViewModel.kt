package com.foodapp.foodapp.presentation.restaurantScreen.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.LocationData
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import com.foodapp.foodapp.presentation.location.LocationInterface
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val geoLocation: LocationInterface,
    private val httpClient: HttpClient,
    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: DetailScreenAction) {
        when (action) {
            is DetailScreenAction.OnNameChanged -> {
                _uiState.update { it.copy(restaurant = it.restaurant!!.copy(restaurantName = action.name)) }
            }
            is DetailScreenAction.OnContactChanged -> {
                _uiState.update { it.copy(restaurant = it.restaurant!!.copy(contactDetails = action.contact)) }
            }
            is DetailScreenAction.OnAddressChanged -> {
                _uiState.update { it.copy(restaurant = it.restaurant!!.copy(address = action.address)) }
            }
            is DetailScreenAction.OnTagsChanged -> {
                _uiState.update { it.copy(restaurant = it.restaurant!!.copy(restaurantTags = action.tags)) }
            }
            is DetailScreenAction.OnImageUploadTrigger -> {
                _uiState.update { it.copy(imageUploadTrigger = uiState.value.imageUploadTrigger.not()) }
            }
            is DetailScreenAction.OnImageSelected -> {
                _uiState.update { it.copy(imageByte = action.byteArray) }
            }
            is DetailScreenAction.OnAddRestaurant -> {
                _uiState.update { it.copy(isLoading = true) }
                addRestaurant()
            }

        }
    }

    fun addRestaurant(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            val locationData = LocationData(
                geoLocation.getCity(httpClient),
                geoLocation.getState(httpClient),
                geoLocation.getCountry(httpClient),
                geoLocation.getPostalCode(httpClient),
                geoLocation.getLatAndLong(httpClient).first,
                geoLocation.getLatAndLong(httpClient).second
            )
            locationData.let {
                val newRestaurant = Restaurant(
                    city = it.city,
                    state = it.state,
                    postelCode = it.postalCode,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    restaurantName = restaurant.restaurantName,
                    restaurantId = restaurant.restaurantId,
                    restaurantTags = restaurant.restaurantTags,
                    address = restaurant.address,
                    restaurantImage = restaurant.restaurantImage,
                    bookingIds = restaurant.bookingIds,
                    reviewIds = restaurant.reviewIds,
                    paymentIds = restaurant.paymentIds,
                    contactDetails = restaurant.contactDetails,
                    totalReviews = restaurant.totalReviews,
                    ratings = restaurant.ratings
                )
                _uiState.update {ui-> ui.copy(restaurant = newRestaurant) }
            }
        }
    }
    private fun addRestaurant(){
        viewModelScope.launch(Dispatchers.IO) {
            restaurantRepository.addRestaurant(uiState.value.restaurant!!).onSuccess {
                restaurantRepository.uploadImage(uiState.value.imageByte!!,uiState.value.restaurant?.restaurantId!!)

                _uiState.update { it.copy(isLoading = false, success = true) }

            }
        }

    }
}