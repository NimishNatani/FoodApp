package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.core.presentation.UiText
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserHomeScreenViewModel(private val restaurantRepository: RestaurantRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UserHomeScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: UserHomeScreenAction) {
        when (action) {
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
                getRestaurantsByCity(
                    city = action.city,
                    selectedCategory = uiState.value.category.second,
                    searchQuery = uiState.value.searchQuery
                )
            }

            is UserHomeScreenAction.OnGettingFoods -> {

            }
        }
    }

    private fun getRestaurantsByCity(
        city: String = "Jaipur",
        selectedCategory: String = "Indian",
        searchQuery: String? = null
    ) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            restaurantRepository.getRestaurantsByCity(city).onSuccess { restaurants ->
                // Filter and sort restaurants
                val filteredRestaurants = if (!searchQuery.isNullOrEmpty()) {
                    println("user vbnevybe :")

                    // Prioritize restaurants matching the search query
                    restaurants.filter { restaurant ->
                        restaurant.restaurantName.contains(searchQuery, ignoreCase = true) ||
                                restaurant.foodItems.any { food ->
                                    food.foodName.contains(searchQuery, ignoreCase = true)
                                }
                    }
                } else {
                    println("Restaurant vbnevybe :")
                    // Filter restaurants by the selected category
                    restaurants.map { restaurant ->
                        val filteredFoods = restaurant.foodItems.filter { food ->
                            food.foodTags.any { it.contains(selectedCategory, ignoreCase = true) }
                        }
                        restaurant.copy(foodItems = filteredFoods)
                    }
                        .filter { it.foodItems.isNotEmpty() } // Remove restaurants without matching foods
                }

                // Combine filtered restaurants and foods
                val allFoods = filteredRestaurants.flatMap { it.foodItems }

                // Update UI state with filtered data
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        searchResults = SearchItem(
                            restaurantList = restaurants,
                            foodList = allFoods
                        )
                    )
                }
            }.onError { error ->
                println("here")
                // Handle the error case
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = UiText.DynamicString(
                            error.toString()
                        )
                    )
                }
            }
        }
    }
}