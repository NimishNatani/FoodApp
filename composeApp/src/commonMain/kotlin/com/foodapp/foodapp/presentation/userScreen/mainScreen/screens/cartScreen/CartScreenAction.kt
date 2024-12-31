package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import com.foodapp.foodapp.domain.models.FoodCart

sealed interface CartScreenAction {
    data object GetFoodCartList : CartScreenAction
}