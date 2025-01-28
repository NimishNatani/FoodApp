package com.foodapp.foodapp.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodCartDetail
import com.foodapp.foodapp.domain.models.Restaurant
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic__favorite
import kotlinproject.composeapp.generated.resources.ic_favorite_border
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun NearestRestaurantCard(
    imageUrl: String?,
    name: String,
    tags: List<String>,
    rating: String?,
    totalReviews: Int,
    distance: String,
    address: String,
    isFavorite: Boolean = false,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .width(250.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Image Section
            Box(modifier = Modifier.wrapContentSize().background(Green)) {
                KamelImage(
                    { asyncPainterResource(data = Url("https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                )
                // Favorite Icon
                Box(
                    modifier = Modifier.wrapContentSize().clip(CircleShape)
                        .align(Alignment.TopStart).padding(2.dp).background(White)
                ) {
                    Icon(
                        painter = painterResource(if (isFavorite) Res.drawable.ic__favorite else Res.drawable.ic_favorite_border), // Replace with your favorite icon
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clickable { onFavoriteClick() }
                    )
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = name,
                    fontSize = TextSize.large,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.height(25.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = tags.joinToString(" | "),
                    fontSize = TextSize.small,
                    color = DarkGrey,
                    modifier = Modifier.height(23.dp)

                )

                Spacer(modifier = Modifier.height(2.dp))

                // Delivery Time, Rating, and LocationInterface
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth().height(20.dp)
                ) {

                    Text(
                        text = distance + "Km",
                        fontSize = TextSize.regular,
                        color = DarkGrey
                    )

                    VerticalDivider(thickness = 1.dp, color = LightGrey)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star, // Replace with your star icon
                            contentDescription = "Rating",
                            tint = SandYellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$rating($totalReviews)",
                            color = SandYellow,
                            fontSize = TextSize.small
                        )
                    }

                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(20.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, // Replace with actual icon
                        contentDescription = "LocationInterface Icon",
                        tint = Green,
                        modifier = Modifier.size(30.dp).padding(top = 5.dp)
                    )
                    Text(
                        text = address,
                        color = DarkGrey,
                        fontSize = TextSize.small
                    )
                }
            }
        }
    }
}

@Composable
fun PopularRestaurant(
    imageUrl: String?,
    name: String,
    rating: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .width(100.dp)
            .height(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(85.dp).background(Green)) {
            KamelImage(
                { asyncPainterResource(data = Url("https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                contentDescription = "Image",
                modifier = Modifier,
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier.wrapContentSize()
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                    .align(Alignment.BottomEnd)
            ) {
                Row(
                    modifier = Modifier.wrapContentSize().background(White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star, // Replace with your star icon
                        contentDescription = "Rating",
                        tint = SandYellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = rating,
                        color = SandYellow,
                        fontSize = TextSize.small
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                }
            }
        }
        Text(
            text = name,
            modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally).padding(8.dp),
            fontSize = TextSize.large,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RestaurantScreenCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier.width(350.dp).padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(), // Add padding around the content
            horizontalAlignment = Alignment.CenterHorizontally // Center all children horizontally
        ) {
            Text(
                text = restaurant.restaurantName,
                modifier = Modifier.padding(vertical = 4.dp),
                fontSize = TextSize.large,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            Text(
                text = restaurant.restaurantTags.joinToString(),
                modifier = Modifier.padding(vertical = 4.dp),
                fontSize = TextSize.regular,
                fontWeight = FontWeight.SemiBold,
                color = DarkGrey
            )

            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Distance",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = TextSize.small,
                        color = DarkGrey
                    )

                    Text(
                        text = "1.5 Km",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = TextSize.small,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                }

                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = "Rating",
                        modifier = Modifier.padding(vertical = 4.dp),
                        fontSize = TextSize.small,
                        color = DarkGrey
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = SandYellow
                        )
                        Text(
                            text = restaurant.ratings.toString(),
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontSize = TextSize.small,
                            fontWeight = FontWeight.SemiBold,
                            color = Black
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn, // Replace with actual icon
                    contentDescription = "LocationInterface Icon",
                    tint = Green,
                    modifier = Modifier.size(30.dp).padding(top = 5.dp)
                )
                Text(
                    text = restaurant.address + restaurant.city + restaurant.state,
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontSize = TextSize.small,
                    fontWeight = FontWeight.SemiBold,
                )
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoodItemCard(
    food: Food,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit,
    onFoodClick: (Food) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
        Card(
            modifier = Modifier
                .width(180.dp)
                .height(265.dp)
                .padding(8.dp).clickable { onFoodClick(food) },
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            with(sharedTransitionScope) {
            Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                KamelImage(
                    { asyncPainterResource(data = Url("https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg")) },
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "restaurantToFoodImage${food.foodId}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
                Box(
                    modifier = Modifier.wrapContentSize().clip(CircleShape)
                        .align(Alignment.TopStart).padding(2.dp).background(White)
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
            }
            Text(
                text = food.foodName,
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.regular,
                modifier = Modifier.padding(4.dp).sharedBounds(
                    rememberSharedContentState(key = "restaurantToFoodName${food.foodId}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )

            Text(
                text = food.foodTags.joinToString(),
                color = DarkGrey,
                fontWeight = FontWeight.SemiBold,
                fontSize = TextSize.small,
                modifier = Modifier.padding(4.dp).sharedBounds(
                    rememberSharedContentState(key = "restaurantToFoodTags${food.foodId}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
            Text(
                text = "₹ " + food.foodDetails.minBy { it.foodPrice }.foodPrice.toString(),
                color = Green,
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.regular,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun FoodItemRow(
    foodCartDetail: FoodCartDetail,
    onAddClick: (FoodCartDetail) -> Unit,
    onSubClick: (FoodCartDetail) -> Unit,
    editable: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Food Size
        Text(
            text = foodCartDetail.foodSize,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = TextSize.regular,
        )
        Spacer(modifier = Modifier.width(10.dp))

        // Food Price
        Text(
            text = "₹ ${foodCartDetail.foodPrice}",
            modifier = Modifier.weight(1f),
            color = Green,
            fontWeight = FontWeight.Bold,
            fontSize = TextSize.regular,
            textAlign = TextAlign.Center
        )

        // Quantity Controls
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Subtract Button
            IconButton(
                enabled = editable,
                onClick = {
                    if (foodCartDetail.quantity > 0) {
                        onSubClick(foodCartDetail.copy(quantity = foodCartDetail.quantity - 1))
                    }
                },
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .border(1.dp, DarkGrey, RoundedCornerShape(8.dp)).size(24.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Subtract",
                    modifier = Modifier.size(24.dp),
                    tint = Green
                )
            }

            // Quantity Display
            Text(
                text = foodCartDetail.quantity.toString(),
                color = Green,
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.regular,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Add Button
            IconButton(
                enabled = editable,
                onClick = {
                    onAddClick(foodCartDetail.copy(quantity = foodCartDetail.quantity + 1))
                },
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .border(1.dp, DarkGrey, RoundedCornerShape(8.dp)).size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Green
                )
            }
        }
    }
}


@Composable
fun FoodCard(
    imageUrl: String?,
    name: String,
    price: String,
    rating: String,
    isFavorite: Boolean = false,
    distance: String,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier.background(
                color = if (isFavorite) Red else SandYellow,
                shape = RoundedCornerShape(12.dp)
            ).fillMaxWidth().height(90.dp)
        ) {
            // Food Image
            AsyncImage(
                model = imageUrl, // Replace with actual image loading
                contentDescription = "Food Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.background(Color.Transparent, CircleShape)
            )
            Row(modifier = Modifier.padding(8.dp).align(Alignment.TopEnd)) {
                Box(
                    modifier = Modifier.background(White, shape = CircleShape).padding(4.dp),
//                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        painter = painterResource(if (isFavorite) Res.drawable.ic__favorite else Res.drawable.ic_favorite_border), // Replace with actual icon
                        contentDescription = "Favorite",
                        tint = Red,
                        modifier = Modifier.clickable { onFavoriteClick() }
                    )
                }
            }
        }


        // Food Details
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.weight(1f)
                )
                Text(
                    text = "$price Rs",
                    color = Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star, // Replace with actual icon
                        contentDescription = "Rating Icon",
                        tint = SandYellow,
                        modifier = Modifier.size(24.dp).padding(top = 6.dp)
                    )
                    Text(
                        text = rating,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, // Replace with actual icon
                        contentDescription = "LocationInterface Icon",
                        tint = Red,
                        modifier = Modifier.size(22.dp).padding(top = 6.dp)
                    )
                    Text(
                        text = "$distance Km Away",
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    image: DrawableResource, // Drawable resource ID
    onSelected: () -> Unit,
    isSelected: Boolean = false
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth() // Allow width to adjust dynamically
            .clickable { onSelected() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        KamelImage(
            {
//            painter = painterResource(image),
                asyncPainterResource(data = Url("https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg"))
            }, contentDescription = "Category Icon",
            modifier = Modifier.size(50.dp).clip(CircleShape)
                .border(width = 1.dp, color = if (isSelected) Green else White, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = name,
            fontSize = TextSize.small,
            color = if (isSelected) Green else Black,
            textAlign = TextAlign.Center,
            maxLines = 1, // Ensure single line
            overflow = TextOverflow.Ellipsis, // Add ellipsis for truncated text
            modifier = Modifier.wrapContentWidth() // Dynamically adjust width based on text
        )

    }
}
