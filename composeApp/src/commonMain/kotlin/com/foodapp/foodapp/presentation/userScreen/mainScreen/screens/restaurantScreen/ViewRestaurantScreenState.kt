package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen

import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

data class ViewRestaurantScreenState(
    val restaurant: Restaurant,
    val selectedFood:Food
)
