package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import coil3.compose.rememberAsyncImagePainter
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
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

                // Delivery Time, Rating, and Location
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
                        contentDescription = "Location Icon",
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
                        contentDescription = "Location Icon",
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
    onSelected: () -> Unit
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
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = name,
            fontSize = TextSize.small,
            color = Black,
            textAlign = TextAlign.Center,
            maxLines = 1, // Ensure single line
            overflow = TextOverflow.Ellipsis, // Add ellipsis for truncated text
            modifier = Modifier.wrapContentWidth() // Dynamically adjust width based on text
        )

    }
}
