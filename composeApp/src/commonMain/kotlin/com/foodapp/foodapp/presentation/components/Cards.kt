package com.foodapp.foodapp.presentation.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.White
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.ic__favorite
import kotlinproject.composeapp.generated.resources.ic_favorite_border
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RestaurantCard(
    imageUrl: String?,
    name: String,
    tags: List<String>,
    rating: String,
    totalReviews: Int,
    distance: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Red),
        shape = RoundedCornerShape(12.dp)

    ) {
        Box(
            modifier = Modifier.background(
                color = SandYellow,
                shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)
            )
                .width(300.dp).height(130.dp)

        ) {
            // Background Image
            AsyncImage(
                model = imageUrl, // Replace with actual image loading
                contentDescription = "Restaurant Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()

            )


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                // Rating Badge
                Box(
                    modifier = Modifier
                        .background(White, RoundedCornerShape(20.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star, // Replace with actual icon
                            contentDescription = "Favorite",
                            tint = SandYellow,
                            modifier = Modifier.clickable { onFavoriteClick() }.size(18.dp)
                        )
                        Text(
                            text = rating,
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "($totalReviews)",
                            color = Black.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                    }
                }

                // Favorite Icon
                Box(modifier = Modifier.background(White, shape = CircleShape).padding(4.dp)) {
                    Icon(
                        painter = painterResource(if (isFavorite) Res.drawable.ic__favorite else Res.drawable.ic_favorite_border), // Replace with actual icon
                        contentDescription = "Favorite",
                        tint = Red,
                        modifier = Modifier.clickable { onFavoriteClick() }
                    )
                }
            }
        }

        // Restaurant Details
        Row(modifier = Modifier.fillMaxSize().background(Red).padding(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = tags.joinToString(separator = " | "),
                    color = White,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = White.copy(alpha = 0.7f),
                    modifier = Modifier.size(14.dp).padding(top=10.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "$distance Km Away",
                    color = White.copy(alpha = 0.7f),
                    fontSize = 12.sp
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
                    modifier = Modifier.background(White, shape = CircleShape).padding(4.dp)
                        ,
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
                        modifier = Modifier.size(12.dp)
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
                        contentDescription = "Rating Icon",
                        tint = Red,
                        modifier = Modifier.size(12.dp)
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
fun CategoryCard(name:String,image:DrawableResource,isSelected:String ,onSelected:()->Unit){
    Card(modifier = Modifier.size(70.dp,30.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected==name) Red else LightGrey),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ){
        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 2.dp).align(Alignment.CenterHorizontally)) {
            Icon(
                painter = painterResource(image),
                contentDescription = "Category Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(name,fontSize = 12.sp, color = if (isSelected==name) White else DarkGrey)
        }
    }

}