package com.foodapp.foodapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.FoodCart
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodCart(foodCartList: List<FoodCart>, screenSize: Pair<Int, Int>) {
    var addToCart by remember {
        mutableStateOf(false)
    }
    val columns =
        if (screenSize.first > 1200) 4 else if (screenSize.first > 800) 3 else if (screenSize.first > 400) 2 else 1

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp)) {
            Text(
                text = foodCartList[0].restaurantName,
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.large,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        HorizontalDivider(thickness = 1.dp, color = GreenShade)


        foodCartList.chunked(columns).forEach { rowItems ->
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { foodCart ->
                    FoodCartList(foodCart = foodCart, onDeleteClick = {})
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
            Text(
                text = "Total Payment",
                fontWeight = FontWeight.Bold,
                color = DarkGrey,
                fontSize = TextSize.regular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "₹ " + foodCartList.sumOf { it.totalPrice }.toString(),
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = TextSize.large,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedCustomButton(
                text = if (addToCart) "Cancel" else "Delete", shape = RoundedCornerShape(10.dp)
            ) {
                if (addToCart) {
                    addToCart = false
                }
            }

            CustomButton(text = if (addToCart) "Order" else "Check Out", buttonColor = Green) {
                if (!addToCart) {
                    addToCart = true
                }
            }
        }
    }
}

@Composable
fun FoodCartList(foodCart: FoodCart, onDeleteClick: () -> Unit) {
    var cardExpand by remember { mutableStateOf(false) }
    var editable by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.width(350.dp).background(White).padding(vertical = 10.dp, horizontal = 12.dp)
            .animateContentSize(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(width = 1.dp, color = DarkGrey),
        elevation = CardDefaults.cardElevation(pressedElevation = 15.dp, hoveredElevation = 15.dp),
        colors = CardDefaults.cardColors(White, White, White, White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            // Pizza Image
            KamelImage(
                { asyncPainterResource(data = Url("https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg")) },
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Title, Description, and Counter
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = foodCart.foodName,
                    maxLines = 2,
                    color = Black,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = foodCart.foodTags.joinToString(),
                    color = DarkGrey,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Edit and Quantity Counter
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            editable = !editable
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = DarkGrey
                        )
                        Text(text = "edit", color = DarkGrey, fontSize = TextSize.small)
                    }
                    Text(
                        text = "View Details",
                        color = DarkGrey,
                        fontSize = TextSize.small,
                        modifier = Modifier.padding(end = 6.dp).clickable {
                            cardExpand = !cardExpand
                        }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Counter
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "Quantity: ", color = DarkGrey, fontSize = TextSize.small)
                    Text(
                        text = foodCart.foodCartDetailsList.sumOf { it.quantity }.toString(),
                        color = Green,
                        fontSize = TextSize.small
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price and Delete Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "₹ " + foodCart.totalPrice.toString(),
                        color = Green,
                        fontSize = TextSize.small,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )

                    IconButton(onClick = { onDeleteClick() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }
        }

        if (cardExpand || editable) {
            foodCart.foodCartDetailsList.forEach {
                if (it.quantity != 0) {
                    FoodItemRow(foodCartDetail = it, onSubClick = {
                        if (editable) {

                        }
                    }, onAddClick = {
                        if (editable) {

                        }
                    })
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedCustomButton(
                    text = if (editable) "Cancel" else "Compress", shape = RoundedCornerShape(10.dp)
                ) {
                    if (editable) {
                        editable = false
                    } else {
                        cardExpand = !cardExpand
                    }
                }

                CustomButton(text = if (editable) "Save" else "Edit", buttonColor = Green) {

                }
            }
        }
    }
}