package com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen

import androidx.lifecycle.ViewModel
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenAction
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FoodDetailScreenViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(FoodDetailScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: FoodDetailScreenAction) {
        when(action){
            is FoodDetailScreenAction.OnImageUploadTrigger -> {
                _uiState.update { it.copy(imageUploadTrigger = uiState.value.imageUploadTrigger.not()) }
            }
            is FoodDetailScreenAction.OnImageSelected -> {
                _uiState.update { it.copy(imageByte = action.byteArray) }
            }
            is FoodDetailScreenAction.AddNewFood -> {
                _uiState.update { it.copy(foodList = it.foodList?.plus(Pair( action.food, action.imageBitmap ))) }
                println(uiState.value.foodList)
            }
        }
    }
    }