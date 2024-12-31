package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.core.presentation.UiText
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserHomeScreenViewModel(private val restaurantRepository: RestaurantRepository,private val screenSize:PlatformConfiguration) :
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
                getFilterRestaurantByCategory(
                    selectedCategory = action.category.second,
                )

            }

            is UserHomeScreenAction.OnGettingRestaurants -> {
                getRestaurantsByCity(
                    city = action.city,
                    onComplete = { getRestaurantAndFood() }
                )
            }

            is UserHomeScreenAction.OnSearch -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
                getRestaurantAndFood(
                    searchQuery = action.query
                )
            }
        }
    }

    private fun getRestaurantsByCity(
        city: String = "Jaipur",
        onComplete: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantRepository.getRestaurantsByCity(city).onSuccess { restaurants ->
                _uiState.update { uiState ->
                    uiState.copy(
                        searchResults = SearchItem(
                            restaurants,
                            restaurants.flatMap { it.foodItems })
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

    private fun getFilterRestaurantByCategory(selectedCategory: String = "Indian") {
        val filterRestaurant = uiState.value.searchResults.restaurantList.filter { restaurant ->
            restaurant.restaurantName.contains(selectedCategory, ignoreCase = true) ||
                    restaurant.restaurantTags.any { tag ->
                        tag.contains(selectedCategory, ignoreCase = true)
                    } || restaurant.foodItems.any { food ->
                food.foodName.contains(selectedCategory, ignoreCase = true) ||
                        food.foodDescription.contains(selectedCategory, ignoreCase = true) ||
                        food.foodTags.any { tag ->
                            tag.contains(selectedCategory, ignoreCase = true)
                        }
            }
        }
        _uiState.update {
            it.copy(
                filterResults = FilterItem(nearestRestaurantList = filterRestaurant)
            )
        }
    }

    private fun getRestaurantAndFood(searchQuery: String? = null) {
        // Preserve the original data for restoration when searchQuery is cleared
        val originalRestaurants = uiState.value.searchResults.restaurantList
        val originalFoods = uiState.value.searchResults.restaurantList.flatMap { it.foodItems }

        if (!searchQuery.isNullOrEmpty()) {
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
            val mealType = getMealTime()
            val popularRestaurantList = originalRestaurants.sortedBy { it.ratings }

            val nearestRestaurantList = originalRestaurants.sortedBy { it.ratings }

            val mealTimeFoodList = originalFoods.filter { food ->
                food.foodType.any { it.contains(mealType, ignoreCase = true) }
            }.sortedBy { food ->
                food.foodType.count { it.contains(mealType, ignoreCase = true) }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    filterResults = FilterItem(
                        popularRestaurantList = popularRestaurantList,
                        nearestRestaurantList = nearestRestaurantList,
                        mealTimeFoodList = mealTimeFoodList
                    )
                )
            }
        }
    }

    fun setRestaurantList(restaurants: List<Restaurant>) {
        _uiState.update {
            it.copy(
                searchResults = SearchItem(restaurantList = restaurants)
            )
        }
    }

    fun getScreenSize():Pair<Int,Int>{
        return Pair(screenSize.screenWidth(),screenSize.screenHeight())
    }
}