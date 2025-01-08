package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import com.foodapp.foodapp.domain.models.FoodCart

sealed interface CartScreenAction {
    data object GetFoodCartList : CartScreenAction
    data class OnSubClick(val foodId: String,val foodSize:String) : CartScreenAction
    data class OnAddClick(val foodId: String,val foodSize:String) : CartScreenAction
    data class OnOrder(val foodCartList: List<FoodCart>) : CartScreenAction
}