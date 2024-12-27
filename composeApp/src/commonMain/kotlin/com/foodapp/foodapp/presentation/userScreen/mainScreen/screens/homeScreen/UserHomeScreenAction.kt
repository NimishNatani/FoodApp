package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant
import org.jetbrains.compose.resources.DrawableResource

sealed interface UserHomeScreenAction {
    data class OnSearchQueryChange(val query: String): UserHomeScreenAction
    data class OnRestaurantClick(val restaurant: Restaurant): UserHomeScreenAction
    data class OnFoodSelected(val food: Food): UserHomeScreenAction
    data class OnFoodNameSelected(val food: Food):UserHomeScreenAction
    data class OnCategorySelected(val category:Pair<DrawableResource,String>):UserHomeScreenAction
    data class OnGettingRestaurants(val city:String):UserHomeScreenAction
    data class OnSearch(val query:String):UserHomeScreenAction
}