package com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodDetailScreenViewModel(    private val restaurantRepository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodDetailScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: FoodDetailScreenAction) {
        when (action) {
            is FoodDetailScreenAction.OnImageUploadTrigger -> {
                _uiState.update { it.copy(imageUploadTrigger = uiState.value.imageUploadTrigger.not()) }
            }

            is FoodDetailScreenAction.OnImageSelected -> {
                _uiState.update { it.copy(imageByte = action.byteArray) }
            }

            is FoodDetailScreenAction.AddNewFood -> {
                _uiState.update {
                    it.copy(
                        foodList = it.foodList?.plus(
                            Triple(
                                action.food,
                                action.imageArray,
                                action.imageBitmap
                            )
                        )
                    )
                }
                println(uiState.value.foodList)
            }

            is FoodDetailScreenAction.AddFoodToRestaurant -> {
                _uiState.update { it.copy(isLoading = true) }
                addFoodToRestaurant()
            }
        }
    }
    private fun addFoodToRestaurant(){
        viewModelScope.launch(Dispatchers.IO){
            restaurantRepository.addFood(uiState.value.foodList!!.map { it.first }).onSuccess {
                uiState.value.foodList!!.forEach {res->
                    restaurantRepository.uploadFoodImage(res.second,res.first.restaurantId+res.first.foodName)

                }
            }
        }
    }
}