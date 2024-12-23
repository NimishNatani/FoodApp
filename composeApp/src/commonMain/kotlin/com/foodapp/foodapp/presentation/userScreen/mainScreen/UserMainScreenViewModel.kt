package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserMainScreenViewModel():ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: MainScreenAction){
        when(action){
            is MainScreenAction.OnSearchQueryChange -> {
                _uiState.value = _uiState.value.copy(searchQuery = action.query)
            }
            is MainScreenAction.OnTabSelected -> {
                _uiState.value = _uiState.value.copy(selectedTabIndex = BottomNavItem(action.index,action.icon))
            }
            is MainScreenAction.OnRestaurantClick -> {

            }
            is MainScreenAction.OnFoodSelected -> {
            }
            is MainScreenAction.OnFoodNameSelected -> {
            }
        }

    }
}