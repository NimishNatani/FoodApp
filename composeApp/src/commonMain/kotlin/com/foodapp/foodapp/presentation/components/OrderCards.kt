package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Booking
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun OrderCard(booking: Booking, page: Int,modifier: Modifier = Modifier) {
    val foodImages = booking.getFoodImages()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(135.dp)
            .width(
                if (page == 0 || page == 1) {
                    330.dp
                } else {
                    250.dp
                }
            )
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image Section
            KamelImage(
                { asyncPainterResource(data = Url(booking.foodCarts[0].foodImage?:"https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            )


            // Details Section
            Column(
                modifier = Modifier.padding(horizontal = 5.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp, top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = booking.foodCarts[0].restaurantName,
                        fontSize = TextSize.large,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.height(25.dp).width(150.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(top = 1.dp, bottom = 4.dp).fillMaxWidth()
                ) {
                    Text(
                        text = "${booking.getTotalQuantity()} Items  ",
                        fontSize = TextSize.regular,
                        color = DarkGrey,
                        modifier = Modifier.height(20.dp)
                    )
                    VerticalDivider(
                        thickness = 2.dp,
                        color = LightGrey,
                        modifier = Modifier.height(20.dp).align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "  1.4km",
                        fontSize = TextSize.regular,
                        color = DarkGrey,
                        modifier = Modifier.padding(start = 4.dp).height(20.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    // Show up to 3 images with overlap
                    Box(modifier = Modifier.fillMaxWidth()) {
                        println("FoodImages: ${foodImages.size}")
                        foodImages.take(3).forEachIndexed { index, imageRes ->
                            KamelImage(
                                { asyncPainterResource(data = Url("https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                                contentDescription = "Item $index",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.padding(start = 20.dp+(index * 20).dp)
                                    .size(30.dp)
                                    .clip(CircleShape)
//                                    .offset(x = (index * 20).dp) // Adjust position for overlap
                            )
                        }
                    }

                    // "+n items" text if additional items exist
                    if (foodImages.size - 3 > 0) {
                        Text(
                            text = "+${foodImages.size} items",
                            fontSize = TextSize.regular,
                            color = DarkGrey,
                            modifier = Modifier.padding(start = 4.dp).height(10.dp)
                        )
                    }
                }
                if (page == 0 || page == 1) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "₹ ${booking.amount}",
                            fontSize = TextSize.large,
                            fontWeight = FontWeight.Bold,
                            color = Green,
                            modifier = Modifier.height(25.dp)
                        )
                        Text(
                            text = if (page == 0) {
                                "Track Order"
                            } else {
                                "Reorder"
                            },
                            color = Green,
                            modifier = Modifier.border(
                                border = BorderStroke(1.dp, color = Green),
                                shape = RoundedCornerShape(6.dp)
                            ).padding( horizontal = 8.dp, vertical = 1.dp)
                                .clickable { }, fontWeight = FontWeight.SemiBold, fontSize = TextSize.regular
                        )
                    }
                }

            }
        }
    }
}

fun Booking.getTotalQuantity(): Int {
    return foodCarts.sumOf { foodCart ->
        foodCart.foodCartDetailsList.sumOf { it.quantity }
    }
}

fun Booking.getFoodImages(): List<String> {
    return foodCarts.map { it.foodImage }
}
