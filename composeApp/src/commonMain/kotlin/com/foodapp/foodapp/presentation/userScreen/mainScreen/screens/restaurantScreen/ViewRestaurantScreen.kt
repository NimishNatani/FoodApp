package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.FoodItemCard
import com.foodapp.foodapp.presentation.components.RestaurantScreenCard
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic__favorite
import kotlinproject.composeapp.generated.resources.ic_favorite_border
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewRestaurantScreenRoot(
    viewModel: ViewRestaurantScreenViewModel = koinViewModel(),
    restaurant: Restaurant,
    onFoodClick: (Food) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.setRestaurant(restaurant = restaurant)

    val screenSize = viewModel.getScreenSize()


    ViewRestaurantScreen(restaurant, onAction = { action ->
        viewModel.onAction(action)
    }, screenSize = screenSize,
        onFoodClick = { onFoodClick(it) }, onBackClick = { onBackClick() })


}

@Composable
fun ViewRestaurantScreen(
//    state: ViewRestaurantScreenState?,
    restaurant: Restaurant,
    onAction: (ViewRestaurantScreenAction) -> Unit,
    onFoodClick: (Food) -> Unit,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    maxImageSize: Dp = 400.dp,
    minImageSize: Dp = 100.dp,
    screenSize: Pair<Int, Int>,
    onBackClick: () -> Unit
) {
    val columns =
        if (screenSize.first > 1200) 4 else if (screenSize.first > 800) 3 else if (screenSize.first > 400) 2 else 1

    var currentImageSize by remember {
        mutableStateOf(maxImageSize)
    }
    var imageScale by remember {
        mutableFloatStateOf(1f)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newImageSize = currentImageSize + delta.dp
                val previousImageSize = currentImageSize
                currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                val consumed = currentImageSize - previousImageSize
                imageScale = currentImageSize / maxImageSize
                return Offset(0f, consumed.value)
            }
        }
    }

    Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
        KamelImage(
            { asyncPainterResource(data = Url("https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg")) },
            contentDescription = "Image",
            modifier = Modifier.fillMaxWidth().height(maxImageSize)
                .clip(RoundedCornerShape(bottomEnd = 80.dp, bottomStart = 80.dp))
                .align(Alignment.TopCenter).graphicsLayer {
                    scaleX = imageScale
                    scaleY = imageScale
                    translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                }, contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier.padding(10.dp).clip(CircleShape).background(White)
                .align(Alignment.TopStart).clickable { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = DarkGrey,
                contentDescription = "arrow",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier.padding(10.dp).clip(CircleShape).background(White)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(if (isFavorite) Res.drawable.ic__favorite else Res.drawable.ic_favorite_border), // Replace with your favorite icon
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .clickable { onFavoriteClick() }
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = if (currentImageSize > 100.dp) currentImageSize - 100.dp else 100.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Restaurant Header
            item {
                RestaurantScreenCard(restaurant = restaurant)
                Spacer(modifier = Modifier.height(20.dp))
            }

//            lazyGrid(columns,restaurant)
            items(restaurant.foodItems.chunked(columns)) { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { food ->
                        FoodItemCard(
                            food = food,
                            onFavoriteClick = {},
                            onFoodClick = { onFoodClick(it) }
                        )
                    }
                }
            }
            // Food Items Grid
        }

    }
}