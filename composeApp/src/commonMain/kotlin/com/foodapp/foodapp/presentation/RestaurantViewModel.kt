package com.foodapp.foodapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import com.foodapp.foodapp.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestaurantViewModel(private  val restaurantRepository: RestaurantRepository):ViewModel() {

    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant = _restaurant.asStateFlow()


    fun getRestaurant(onSuccess: () -> Unit,onFailure: () -> Unit) {
        viewModelScope.launch {
            restaurantRepository.getRestaurantByJwttoken().onSuccess {result ->
                _restaurant.value = result
                if (restaurant.value==result) {
                    onSuccess()
                }
            }.onError { onFailure() }

        }
    }

    fun setRestaurant(restaurant:Restaurant?){
        _restaurant.value = restaurant
    }
}