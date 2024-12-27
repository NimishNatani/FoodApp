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
                _uiState.value = _uiState.value.copy(category = action.category, isLoading = true)
                getRestaurantAndFood(
                    selectedCategory = action.category.second,
                )

            }

            is UserHomeScreenAction.OnGettingRestaurants -> {
                getRestaurantsByCity(
                    city = action.city,
                    onComplete = {getRestaurantAndFood(selectedCategory = uiState.value.category.second)}
                )
            }

            is UserHomeScreenAction.OnSearch -> {
                _uiState.value = _uiState.value.copy( isLoading = true)
                getRestaurantAndFood(
                    selectedCategory = uiState.value.category.second,
                    searchQuery = action.query
                )
            }
        }
    }

    private fun getRestaurantsByCity(
        city: String = "Jaipur",
        onComplete:()->Unit
    ) {
        viewModelScope.launch {
            restaurantRepository.getRestaurantsByCity(city).onSuccess { restaurants ->
                _uiState.update {uiState ->
                    uiState.copy(
                        searchResults =  SearchItem(restaurants, restaurants.flatMap { it.foodItems })
                    )
                }
                onComplete()
            }.onError { error ->
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

    private fun getRestaurantAndFood(selectedCategory: String = "Indian",
                                     searchQuery: String? = null){
        // Preserve the original data for restoration when searchQuery is cleared
        val originalRestaurants = uiState.value.searchResults.restaurantList
        val originalFoods = uiState.value.searchResults.restaurantList.flatMap { it.foodItems }

        val filteredRestaurants = if (!searchQuery.isNullOrEmpty()) {
            // Filter and sort restaurants and food based on the search query
            val filteredAndSortedRestaurants = originalRestaurants.filter { restaurant ->
                restaurant.restaurantName.contains(searchQuery, ignoreCase = true) ||
                        restaurant.address.contains(searchQuery, ignoreCase = true) ||
                        restaurant.city.contains(searchQuery, ignoreCase = true) ||
                        restaurant.restaurantTags.any { tag ->
                            tag.contains(searchQuery, ignoreCase = true)
                        } ||
                        restaurant.foodItems.any { food ->
                            food.foodName.contains(searchQuery, ignoreCase = true) ||
                                    food.foodDescription.contains(searchQuery, ignoreCase = true) ||
                                    food.foodTags.any { tag ->
                                        tag.contains(searchQuery, ignoreCase = true)
                                    }
                        }
            }.sortedWith(compareBy(
                { it.restaurantName.contains(searchQuery, ignoreCase = true) },
                { it.city.contains(searchQuery, ignoreCase = true) },
                { it.address.contains(searchQuery, ignoreCase = true) }
            ))

            val filteredAndSortedFoods = originalFoods.filter { food ->
                food.foodName.contains(searchQuery, ignoreCase = true) ||
                        food.foodDescription.contains(searchQuery, ignoreCase = true) ||
                        food.foodTags.any { tag ->
                            tag.contains(searchQuery, ignoreCase = true)
                        }
            }.sortedWith(compareBy(
                { it.foodName.contains(searchQuery, ignoreCase = true) },
                { it.foodTags.any { tag -> tag.contains(searchQuery, ignoreCase = true) } },
                { it.foodDescription.contains(searchQuery, ignoreCase = true) }
            ))

            // Return both filtered and sorted restaurants and foods
            Pair(filteredAndSortedRestaurants, filteredAndSortedFoods)

        } else {
            val filteredRestaurants = originalRestaurants.filter { restaurant ->
                restaurant.restaurantTags.any { it.contains(selectedCategory, ignoreCase = true) }
            }.sortedBy { restaurant ->
                restaurant.restaurantTags.count { it.contains(selectedCategory, ignoreCase = true) }
            }

            val filteredFoods = originalFoods.filter { food ->
                food.foodTags.any { it.contains(selectedCategory, ignoreCase = true) }
            }.sortedBy { food ->
                food.foodTags.count { it.contains(selectedCategory, ignoreCase = true) }
            }
            Pair(filteredRestaurants, filteredFoods)
        }

        // Update UI state with filtered and sorted data
        _uiState.update {
            it.copy(
                isLoading = false,
                filterResults = SearchItem(
                    restaurantList = filteredRestaurants.first,
                    foodList = filteredRestaurants.second
                )
            )
        }
    }
}