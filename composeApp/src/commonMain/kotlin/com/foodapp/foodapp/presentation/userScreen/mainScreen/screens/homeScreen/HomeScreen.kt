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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
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
        Pair(Res.drawable.compose_multiplatform,"Indian"),
        Pair(Res.drawable.compose_multiplatform,"Chinese"),
        Pair(Res.drawable.compose_multiplatform,"Ice Cream"),
        Pair(Res.drawable.compose_multiplatform,"Italian"),
        Pair(Res.drawable.compose_multiplatform,"Fast Food"),
        Pair(Res.drawable.compose_multiplatform,"Sweets"),
        Pair(Res.drawable.compose_multiplatform,"Drinks")
    )
    // List of Restaurants
    val restaurants = listOf(
        Restaurant(
            restaurantId = "R001",
            restaurantImage = "image_url_1",
            restaurantName = "Spicy Paradise",
            contactDetails = "+1234567890",
            latitude = 12.9716,
            longitude = 77.5946,
            address = "123 Food Street",
            city = "Bangalore",
            state = "Karnataka",
            postalCode = "560001",
            totalReviews = 120,
            ratings = 4.5,
        ),
        Restaurant(
            restaurantId = "R002",
            restaurantImage = "image_url_2",
            restaurantName = "Green Veggie Delight",
            contactDetails = "+9876543210",
            latitude = 19.0760,
            longitude = 72.8777,
            address = "456 Veggie Lane",
            city = "Mumbai",
            state = "Maharashtra",
            postalCode = "400001",
            totalReviews = 85,
            ratings = 4.2,
        )
    )

// List of Foods
    val foods = listOf(
        Food(
            foodId = "F001",
            foodName = "Paneer Butter Masala",
            foodDescription = "Rich and creamy paneer curry.",
            foodImage = "food_image_1",
            foodDetails = listOf(
                FoodDetails(foodSize = "Half", foodPrice = 150.0),
                FoodDetails(foodSize = "Full", foodPrice = 250.0)
            ),
            isAvailable = true,
            isVeg = true,
            rating = 4.6,
            totalReviews = 50,
            restaurantId = "R001"
        ),
        Food(
            foodId = "F002",
            foodName = "Chicken Biryani",
            foodDescription = "Aromatic and flavorful chicken biryani.",
            foodImage = "food_image_2",
            foodDetails = listOf(
                FoodDetails(foodSize = "Single", foodPrice = 200.0),
                FoodDetails(foodSize = "Family", foodPrice = 500.0)
            ),
            isAvailable = true,
            isVeg = false,
            rating = 4.8,
            totalReviews = 80,
            restaurantId = "R001"
        ),
        Food(
            foodId = "F003",
            foodName = "Veggie Burger",
            foodDescription = "Fresh veggie patty with sauces and buns.",
            foodImage = "food_image_3",
            foodDetails = listOf(
                FoodDetails(foodSize = "Regular", foodPrice = 100.0),
                FoodDetails(foodSize = "Large", foodPrice = 150.0)
            ),
            isAvailable = true,
            isVeg = true,
            rating = 4.4,
            totalReviews = 30,
            restaurantId = "R002"
        )
    )

    if (state.isLoading) {
        Text("Loading")
    } else if (!state.isLoading && state.errorMessage != null) {
        Text("Error")
    } else {
        Column(modifier = Modifier.fillMaxSize().background(White).verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Find The Best Food & Restaurants",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.weight(1f)
                )
                Box(modifier = Modifier.background(SandYellow, CircleShape)){
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notification",
                        tint = DarkGrey,
                        modifier = Modifier.padding(horizontal = 18.dp).size(30.dp)
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                SearchBar(
                    searchQuery = state.searchQuery,
                    onSearchQueryChange = {
                        onAction(UserHomeScreenAction.OnSearchQueryChange(it))
                    },
                    onImeSearch = {},
                    modifier = Modifier.weight(1f)
                )
                Box(modifier = Modifier.background(LightGrey, CircleShape)){
                    Icon(
                        painter = painterResource(Res.drawable.ic_sort),
                        contentDescription = "Notification",
                        tint = DarkGrey,
                        modifier = Modifier.padding(horizontal = 18.dp).size(50.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                categoryList.forEach {
                    CategoryCard(name = it.second, image = it.first,isSelected = state.category.second, onSelected = {})
                    Spacer(modifier = Modifier.width(8.dp))

                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp)) {
                Text(
                    "Trending Restaurants",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f))
                Row {
                    Text("View All", color = Red, fontSize = 16.sp, modifier = Modifier.padding(top=6.dp))
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_forward),
                        contentDescription = "arrow",
                        tint = Red,
                        )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp).horizontalScroll(
                rememberScrollState()
            )){
                restaurants.forEach { restaurant ->
                    RestaurantCard(
                        imageUrl = restaurant.restaurantImage,
                        name = restaurant.restaurantName,
                        tags = listOf("Tag1", "Tag2", "Tag3"),
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
                    modifier = Modifier.weight(1f))
                Row {
                    Text("View All", color = Red, fontSize = 16.sp, modifier = Modifier.padding(top=6.dp))
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_forward),
                        contentDescription = "arrow",
                        tint = Red,
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 10.dp).horizontalScroll(
                rememberScrollState()
            )){
                foods.forEach { food ->
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