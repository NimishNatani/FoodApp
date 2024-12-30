package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen

import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.models.FoodCartDetail

sealed interface ViewFoodScreenAction {

   data class onAddClick(val foodItemDetail: FoodCartDetail):ViewFoodScreenAction
   data class onSubClick(val foodItemDetail: FoodCartDetail):ViewFoodScreenAction
   data class onCartClick(val foodCart: FoodCart) : ViewFoodScreenAction
}