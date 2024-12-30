package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen

import com.foodapp.foodapp.domain.models.Food

sealed interface ViewRestaurantScreenAction {

    data class OnFoodSelected(val food: Food): ViewRestaurantScreenAction

}