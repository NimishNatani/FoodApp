package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen

import androidx.lifecycle.ViewModel
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodItem
import com.foodapp.foodapp.domain.models.FoodItemDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewFoodScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FoodItem?>(null)
    val uiState = _uiState.asStateFlow()

    fun onAction(action: ViewFoodScreenAction) {
        when (action) {
            is ViewFoodScreenAction.onAddClick -> {
                _uiState.update {
                    it?.copy(foodItemDetails = uiState.value!!.foodItemDetails.map { item ->
                        if (item.foodSize == action.foodItemDetail.foodSize) {
                            action.foodItemDetail // Update the specific item
                        } else {
                            item // Keep other items unchanged
                        }
                    }, totalPrice = uiState.value!!.totalPrice+action.foodItemDetail.foodPrice)
                }
            }

            is ViewFoodScreenAction.onSubClick -> {
                _uiState.update {
                    it?.copy(foodItemDetails = uiState.value!!.foodItemDetails.map { item ->
                        if (item.foodSize == action.foodItemDetail.foodSize) {
                            action.foodItemDetail // Update the specific item
                        } else {
                            item // Keep other items unchanged
                        }
                    }, totalPrice = uiState.value!!.totalPrice-action.foodItemDetail.foodPrice)
                }
            }

        }
    }

    fun updateFoodItem(food: Food) {
        _uiState.value = FoodItem(

            foodId = food.foodId,
            foodName = food.foodName,
            foodImage = food.foodImage,
            foodTags = food.foodTags,
            foodDescription = food.foodDescription,
            foodItemDetails = food.foodDetails.map { foodDetail ->
                FoodItemDetails(
                    foodSize = foodDetail.foodSize,
                    foodPrice = foodDetail.foodPrice,
                )
            }, restaurantId = food.restaurantId
        )

    }


}