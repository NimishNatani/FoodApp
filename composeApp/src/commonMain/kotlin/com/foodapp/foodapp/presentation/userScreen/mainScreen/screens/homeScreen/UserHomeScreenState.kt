package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import com.foodapp.core.presentation.UiText
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource

data class UserHomeScreenState(
    val searchQuery: String = "",
    val searchResults: SearchItem = SearchItem(),
    val filterResults:SearchItem = SearchItem(),
    val favoriteRestaurant: List<Restaurant> = emptyList(),
    val favoriteFood: List<Food> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null,
    var category:Pair<DrawableResource,String> = Pair(Res.drawable.compose_multiplatform,"Indian")
)

data class SearchItem (
    val restaurantList:List<Restaurant> = emptyList<Restaurant>(),
    val foodList:List<Food> = emptyList<Food>()
)
