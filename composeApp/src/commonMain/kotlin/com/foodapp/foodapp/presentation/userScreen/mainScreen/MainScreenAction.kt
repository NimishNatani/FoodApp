package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.ui.graphics.vector.ImageVector
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

sealed interface MainScreenAction {
    data class OnSearchQueryChange(val query: String): MainScreenAction
    data class OnRestaurantClick(val restaurant: Restaurant): MainScreenAction
    data class OnTabSelected(val index: Int,val icon:ImageVector): MainScreenAction
    data class OnFoodSelected(val food: Food): MainScreenAction
    data class OnFoodNameSelected(val food: Food):MainScreenAction
}