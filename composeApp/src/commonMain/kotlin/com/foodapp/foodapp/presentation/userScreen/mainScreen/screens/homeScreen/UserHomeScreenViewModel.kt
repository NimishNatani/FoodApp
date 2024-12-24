package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserHomeScreenViewModel():ViewModel() {

    private val _uiState = MutableStateFlow(UserHomeScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: UserHomeScreenAction){
        when(action) {
            is UserHomeScreenAction.OnSearchQueryChange -> {
                _uiState.value = _uiState.value.copy(searchQuery = action.query)
            }

            is UserHomeScreenAction.OnRestaurantClick -> {

            }

            is UserHomeScreenAction.OnFoodSelected -> {

            }

            is UserHomeScreenAction.OnFoodNameSelected -> {

            }
            is UserHomeScreenAction.OnCategorySelected -> {

            }
            is UserHomeScreenAction.OnGettingRestaurants -> {

            }
            is UserHomeScreenAction.OnGettingFoods -> {

            }
        }
    }
}