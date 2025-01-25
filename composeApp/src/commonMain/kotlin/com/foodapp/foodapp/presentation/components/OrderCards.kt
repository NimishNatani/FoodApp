package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun OrderCard(booking: Booking, page: Int) {
    val foodImages = booking.getFoodImages()
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp)
            .width(if (page==0||page==1){350.dp}else{250.dp})
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
                { asyncPainterResource(data = Url("https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                contentDescription = "Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )


            // Details Section
            Column(
                modifier = Modifier.padding(horizontal = 2.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = booking.foodCarts[0].restaurantName,
                        fontSize = TextSize.large,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.height(25.dp)
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
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
                ) {
                    Text(
                        text = "${booking.getTotalQuantity()} Items",
                        fontSize = TextSize.regular,
                        color = DarkGrey,
                        modifier = Modifier.height(15.dp)
                    )
                    VerticalDivider(thickness = 1.dp, color = LightGrey)
                    Text(
                        text = "1.4km",
                        fontSize = TextSize.regular,
                        color = DarkGrey,
                        modifier = Modifier.padding(start = 4.dp).height(15.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    // Show up to 3 images with overlap
                    foodImages.take(3).forEachIndexed { index, imageRes ->
                        KamelImage(
                            { asyncPainterResource(data = Url("https://t3.ftcdn.net/jpg/03/24/73/92/360_F_324739203_keeq8udvv0P2h1MLYJ0GLSlTBagoXS48.jpg")) },
                            contentDescription = "Item $index",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .padding(start = if (index > 0) (-10).dp else 0.dp,end = 8.dp) // Overlap effect
                        )
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "₹ ${booking.amount}",
                            fontSize = TextSize.large,
                            fontWeight = FontWeight.Bold,
                            color = Green,
                            modifier = Modifier.height(25.dp)
                        )
                        OutlinedCustomButton(
                            text = if (page == 0) {
                                "Track Order"
                            } else {
                                "Reorder"
                            },
                            shape = RoundedCornerShape(8.dp),
                            onClick = {},
                            textColor = Green,
                            buttonColor = Green
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
