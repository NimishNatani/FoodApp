@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.CategoryCard
import com.foodapp.foodapp.presentation.components.FoodCategoryCard
import com.foodapp.foodapp.presentation.components.NearestRestaurantCard
import com.foodapp.foodapp.presentation.components.PopularRestaurant
import com.foodapp.foodapp.presentation.components.SearchBar
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserHomeScreenRoot(
    viewModel: UserHomeScreenViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onNotificationClick: () -> Unit,
    onViewAllRestaurantScreen: (List<Restaurant>) -> Unit,
    onViewAllCategoryScreen: (List<Restaurant>) -> Unit,
    onViewCategoryItem: (Pair<DrawableResource, String>) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onFoodSelected: (Food,Restaurant) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
//    LaunchedEffect(Unit) {
//        viewModel.onAction(UserHomeScreenAction.OnGettingRestaurants("Jaipur"))
//    }
    UserHomeScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        },
        onViewAllRestaurantScreen = { onViewAllRestaurantScreen(it) },
        onViewAllCategoryScreen = { onViewAllCategoryScreen(it) },
        onViewCategoryItem = { onViewCategoryItem(it) },
        onRestaurantClick = { onRestaurantClick(it) },
        onFoodSelected = {food,restaurant -> onFoodSelected(food,restaurant,) },
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope
    )

}

@Composable
fun UserHomeScreen(
    state: UserHomeScreenState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope,
    onAction: (UserHomeScreenAction) -> Unit,
    onViewAllRestaurantScreen: (List<Restaurant>) -> Unit,
    onViewAllCategoryScreen: (List<Restaurant>) -> Unit,
    onViewCategoryItem: (Pair<DrawableResource, String>) -> Unit,
    onRestaurantClick: (Restaurant) -> Unit,
    onFoodSelected: (Food,Restaurant) -> Unit

) {
    val categoryList = listOf(
        Pair(Res.drawable.compose_multiplatform, "Indian"),
        Pair(Res.drawable.compose_multiplatform, "Chinese"),
        Pair(Res.drawable.compose_multiplatform, "Ice Cream"),
        Pair(Res.drawable.compose_multiplatform, "Italian"),
        Pair(Res.drawable.compose_multiplatform, "Fast Food"),
        Pair(Res.drawable.compose_multiplatform, "Sweets"),
        Pair(Res.drawable.compose_multiplatform, "Drinks")
    )
    val mealTime = getMealTime()
    // List of Restaurants


    if (state.isLoading) {
        Text("Loading")
    } else if (!state.isLoading && state.errorMessage != null) {
        Text(state.errorMessage.asString())
    } else {
        Column(
            modifier = Modifier.fillMaxSize().background(White)
                .verticalScroll(rememberScrollState())
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 15.dp)) {
                Text(
                    "Find The Best Food \n& Restaurants",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification",
                    tint = GreenShade,
                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp).size(30.dp)
                )

            }
            HorizontalDivider(thickness = 1.dp, color = LightGrey)

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                SearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = {
                        onAction(UserHomeScreenAction.OnSearchQueryChange(it))
                    },
                    onImeSearch = {
                        onAction(UserHomeScreenAction.OnSearch(query = state.searchQuery))
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Categories",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "arrow",
                    tint = GreenShade,
                    modifier = Modifier.padding(top = 1.dp)
                        .clickable { onViewAllCategoryScreen(state.searchResults.restaurantList) }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                categoryList.forEach {
                    CategoryCard(
                        name = it.second,
                        image = it.first,
                        onSelected = {
                            onViewCategoryItem(it)
                        })
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "$mealTime Time",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "arrow",
                    tint = GreenShade,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    ), horizontalArrangement = Arrangement.Start
            ) {
                state.filterResults.mealTimeFoodList.take(6).forEach { food ->
                    FoodCategoryCard(
                        foodId = food.foodId,
                        name = food.foodName,
                        image = Res.drawable.compose_multiplatform,
                        onSelected = {

                            state.searchResults.restaurantList.find { it.restaurantId == food.restaurantId }
                                ?.let {
                                    food.transitionTag = "${food.foodId}Food_Time"
                                    onFoodSelected(food, it) }
                        },
                        transitionTag = "${food.foodId}Food_Time",
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Nearest to You",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "arrow",
                    tint = GreenShade,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                state.filterResults.nearestRestaurantList.take(6).forEach { restaurant ->
                    NearestRestaurantCard(
                        restaurantId = restaurant.restaurantId!!,
                        imageUrl = restaurant.restaurantImage,
                        name = restaurant.restaurantName,
                        tags = restaurant.restaurantTags,
                        rating = restaurant.ratings.toString(),
                        totalReviews = restaurant.totalReviews,
                        distance = "1.5",
                        isFavorite = false,
                        onFavoriteClick = {},
                        address = restaurant.address + restaurant.city + restaurant.state,
                        transitionTag ="${restaurant.restaurantId}Near" ,
                        onClick = {
                            restaurant.transitionTag = "${restaurant.restaurantId}Near"
                            onRestaurantClick(restaurant) },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Popular Now",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "arrow",
                    tint = GreenShade,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                state.filterResults.popularRestaurantList.take(6)
                    .forEach { restaurant ->
                        PopularRestaurant(
                            imageUrl = restaurant.restaurantImage,
                            name = restaurant.restaurantName,
                            rating = restaurant.ratings.toString(),
                            onClick = {
                                restaurant.transitionTag = "${restaurant.restaurantId}Popular"
                                onRestaurantClick(restaurant) },
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                    }
            }
        }
    }
}

fun getMealTime(): String {
    // Get the current time in the system's local timezone
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

    return when (currentTime.hour) {
        in 4..12 -> "Breakfast"
        in 13..17 -> "Lunch"
        in 18..24 -> "Dinner"
        in 0..3 -> "Dinner"
        else -> "other"
    }
}