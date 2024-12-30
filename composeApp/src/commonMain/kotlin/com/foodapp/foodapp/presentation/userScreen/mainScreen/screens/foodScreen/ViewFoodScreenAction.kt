package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen

import com.foodapp.foodapp.domain.models.FoodItemDetails

sealed interface ViewFoodScreenAction {

   data class onAddClick(val foodItemDetail: FoodItemDetails):ViewFoodScreenAction
   data class onSubClick(val foodItemDetail: FoodItemDetails):ViewFoodScreenAction
}