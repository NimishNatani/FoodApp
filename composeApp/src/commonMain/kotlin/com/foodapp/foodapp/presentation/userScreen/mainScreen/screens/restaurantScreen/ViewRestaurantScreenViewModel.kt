package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen

import androidx.lifecycle.ViewModel
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewRestaurantScreenViewModel(private val screenSize:PlatformConfiguration):ViewModel() {

    private val _uiState = MutableStateFlow<ViewRestaurantScreenState?>(null)
    val uiState = _uiState.asStateFlow()

    fun onAction(action: ViewRestaurantScreenAction) {
        when (action) {
            is ViewRestaurantScreenAction.OnFoodSelected -> {
                _uiState.value = _uiState.value?.copy(selectedFood = action.food)
            }
        }
    }

    fun getScreenSize():Pair<Float,Float>{
        return Pair(screenSize.screenWidth(),screenSize.screenHeight())
    }

    fun setRestaurant(restaurant: Restaurant){
        _uiState.update {
            it?.copy(restaurant = restaurant)
        }

    }

}