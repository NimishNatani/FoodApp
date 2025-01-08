package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.CategoryCard
import com.foodapp.foodapp.presentation.components.NearestRestaurantCard
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewAllCategoryScreenRoot(
    restaurants: List<Restaurant>,
    viewModel: UserHomeScreenViewModel = koinViewModel(),
    onRestaurantClick: (Restaurant) -> Unit,
    onBackClick:()->Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.setRestaurantList(restaurants)
    val screenSize = viewModel.getScreenSize()

    ViewAllCategoryScreen(state, onAction = { action ->
        viewModel.onAction(action)
    },  screenSize = screenSize,
        onRestaurantClick = { restaurant ->
            onRestaurantClick(restaurant)
        }, onBackClick = {onBackClick()})


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAllCategoryScreen(
    state: UserHomeScreenState,
    onAction: (UserHomeScreenAction) -> Unit,
    onBackClick: () -> Unit,
    selectedName: String = "Pizza",
    screenSize: Pair<Int, Int>,
    onRestaurantClick: (Restaurant) -> Unit,
) {

    val columns = if (screenSize.first > 1800) 3 else if (screenSize.first > 1200) 2 else 1

    val categoryList = listOf(
        Pair(Res.drawable.compose_multiplatform, "Indian"),
        Pair(Res.drawable.compose_multiplatform, "Pizza"),
        Pair(Res.drawable.compose_multiplatform, "Burger"),
        Pair(Res.drawable.compose_multiplatform, "Panner"),
        Pair(Res.drawable.compose_multiplatform, "Fries"),
        Pair(Res.drawable.compose_multiplatform, "Chinese"),
        Pair(Res.drawable.compose_multiplatform, "Veg"),
        Pair(Res.drawable.compose_multiplatform, "Non-Veg"),
        Pair(Res.drawable.compose_multiplatform, "Ice Cream"),
        Pair(Res.drawable.compose_multiplatform, "Italian"),
        Pair(Res.drawable.compose_multiplatform, "Fast Food"),
        Pair(Res.drawable.compose_multiplatform, "Sweets"),
        Pair(Res.drawable.compose_multiplatform, "Drinks")
    )

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        TopAppBar(
            title = {
                Text(
                    text = "Categories",
                    fontSize = TextSize.large,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = DarkGrey,
                    contentDescription = "arrow",
                    modifier = Modifier.padding(start = 4.dp).clickable { onBackClick() }
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            )

        )
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categoryList.forEach { category ->
                CategoryCard(
                    name = category.second,
                    image = category.first,
                    onSelected = {
                        onAction(UserHomeScreenAction.OnCategorySelected(category))
                    },
                    isSelected = state.category.second == category.second
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Restaurants", fontSize = TextSize.large, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(5.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filterResults.nearestRestaurantList) { restaurant ->
                NearestRestaurantCard(
                    imageUrl = restaurant.restaurantImage,
                    tags = restaurant.restaurantTags,
                    name = restaurant.restaurantName,
                    address = "${restaurant.address}, ${restaurant.city}, ${restaurant.state}",
                    rating = restaurant.ratings.toString(),
                    isFavorite = false,
                    onClick = { onRestaurantClick(restaurant) },
                    onFavoriteClick = {},
                    totalReviews = restaurant.totalReviews,
                    distance = "1.5"
                )
            }
        }
    }
}