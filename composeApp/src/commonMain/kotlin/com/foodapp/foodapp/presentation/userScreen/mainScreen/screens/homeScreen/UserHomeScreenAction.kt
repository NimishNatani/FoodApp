package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

sealed interface UserHomeScreenAction {
    data class OnSearchQueryChange(val query: String): UserHomeScreenAction
    data class OnRestaurantClick(val restaurant: Restaurant): UserHomeScreenAction
    data class OnFoodSelected(val food: Food): UserHomeScreenAction
    data class OnFoodNameSelected(val food: Food):UserHomeScreenAction
    data class OnCategorySelected(val category:String):UserHomeScreenAction
    data class OnGettingRestaurants(val searchQuery:String):UserHomeScreenAction
    data class OnGettingFoods(val searchQuery:String):UserHomeScreenAction
}