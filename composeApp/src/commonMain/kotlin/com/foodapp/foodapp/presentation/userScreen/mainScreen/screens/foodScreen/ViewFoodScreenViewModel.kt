package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.models.FoodCartDetail
import com.foodapp.foodapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewFoodScreenViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<FoodCart?>(null)
    val uiState = _uiState.asStateFlow()

    fun onAction(action: ViewFoodScreenAction) {
        when (action) {
            is ViewFoodScreenAction.onAddClick -> {
                _uiState.update {
                    it?.copy(foodCartDetailsList = uiState.value!!.foodCartDetailsList.map { item ->
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
                    it?.copy(foodCartDetailsList = uiState.value!!.foodCartDetailsList.map { item ->
                        if (item.foodSize == action.foodItemDetail.foodSize) {
                            action.foodItemDetail // Update the specific item
                        } else {
                            item // Keep other items unchanged
                        }
                    }, totalPrice = uiState.value!!.totalPrice-action.foodItemDetail.foodPrice)
                }
            }
            is ViewFoodScreenAction.onCartClick -> {
                if (action.foodCart.totalPrice != 0.0) {
                    viewModelScope.launch {
                        userRepo.addItemToCart(action.foodCart)
                    }
                }
            }

        }
    }

    fun updateFoodItem(food: Food,restaurantName: String) {
        _uiState.value = FoodCart(

            foodId = food.foodId,
            foodName = food.foodName,
            foodImage = food.foodImage,
            foodTags = food.foodTags,
            foodDescription = food.foodDescription,
            foodCartDetailsList = food.foodDetails.map { foodDetail ->
                FoodCartDetail(
                    foodSize = foodDetail.foodSize,
                    foodPrice = foodDetail.foodPrice,
                    quantity = 0
                )
            }, restaurantId = food.restaurantId,
            restaurantName = restaurantName,
            transitionTag = food.transitionTag
        )
    }


}