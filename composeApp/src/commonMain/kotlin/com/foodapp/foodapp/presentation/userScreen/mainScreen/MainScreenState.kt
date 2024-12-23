package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.foodapp.core.presentation.UiText
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

data class MainScreenState (
    val searchQuery: String = "Pizza",
    val searchResults: SearchItem = SearchItem(),
    val favoriteRestaurant: List<Restaurant> = emptyList(),
    val favoriteFood: List<Food> = emptyList(),
    val isLoading: Boolean = true,
    var selectedTabIndex: BottomNavItem = BottomNavItem(0,Icons.Default.Home),
    val errorMessage: UiText? = null
)


data class SearchItem (
   val restaurantList:List<Restaurant> = emptyList<Restaurant>(),
   val foodList:List<Food> = emptyList<Food>()
)