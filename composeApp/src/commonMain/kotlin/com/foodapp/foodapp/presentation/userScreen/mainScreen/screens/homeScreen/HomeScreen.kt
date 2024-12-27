package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodDetails
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.CategoryCard
import com.foodapp.foodapp.presentation.components.FoodCard
import com.foodapp.foodapp.presentation.components.RestaurantCard
import com.foodapp.foodapp.presentation.components.SearchBar
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.ic_arrow_forward
import kotlinproject.composeapp.generated.resources.ic_sort
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserHomeScreenRoot(
    viewModel: UserHomeScreenViewModel = koinViewModel(),
    onNotificationClick: () -> Unit
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
    )

}

@Composable
fun UserHomeScreen(
    state: UserHomeScreenState,
    onAction: (UserHomeScreenAction) -> Unit
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
                Box(modifier = Modifier.background(SandYellow, CircleShape).padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        tint = DarkGrey,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp).size(30.dp)
                    )
                }
            }

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
                Spacer(modifier = Modifier.width(5.dp))
                Box(modifier = Modifier.background(LightGrey, RoundedCornerShape(15.dp))) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_sort),
                        contentDescription = "Notification",
                        tint = DarkGrey,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp).size(40.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                categoryList.forEach {
                    CategoryCard(
                        name = it.second,
                        image = it.first,
                        isSelected = state.category.second,
                        onSelected = {onAction(UserHomeScreenAction.OnCategorySelected(Pair(it.first,it.second)))})
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Trending Restaurants",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    Text("View All", color = Red, fontSize = 16.sp, modifier = Modifier)
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_forward),
                        contentDescription = "arrow",
                        tint = Red,
                        modifier = Modifier.padding(top = 1.dp)

                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                state.filterResults.restaurantList.take(6).forEach { restaurant ->
                    RestaurantCard(
                        imageUrl = restaurant.restaurantImage,
                        name = restaurant.restaurantName,
                        tags = restaurant.restaurantTags,
                        rating = restaurant.ratings.toString(),
                        totalReviews = restaurant.totalReviews,
                        distance = "1.5",
                        isFavorite = false,
                        onFavoriteClick = {}

                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Trending Foods",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    Text("View All", color = Red, fontSize = 16.sp, modifier = Modifier)
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_forward),
                        contentDescription = "arrow",
                        tint = Red,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
            ) {
                state.filterResults.foodList.take(6).forEach { food ->
                    FoodCard(
                        imageUrl = food.foodImage,
                        name = food.foodName,
                        rating = food.rating.toString(),
                        distance = "1.5",
                        isFavorite = false,
                        onFavoriteClick = {},
                        price = food.foodDetails[0].foodPrice.toString()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}