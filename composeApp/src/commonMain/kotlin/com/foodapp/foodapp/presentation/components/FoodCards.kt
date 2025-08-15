package com.foodapp.foodapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.FoodCart
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodCart(
    foodCartList: List<FoodCart>,
    screenSize: Pair<Float, Float>,
    onSubClick: (String, String) -> Unit,
    onAddClick: (String, String) -> Unit,
    onOrder:(List<FoodCart>) -> Unit
) {
    var addToCart by remember {
        mutableStateOf(false)
    }
    val columns =
        if (screenSize.first > 1200) 4 else if (screenSize.first > 800) 3 else if (screenSize.first > 400) 2 else 1

    val paymentMethods = listOf("On delivery","upi", "PayPal", "Visa", "Mastercard")
    var selectedMethod by remember { mutableStateOf(paymentMethods[0]) }

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
                    FoodCartList(foodCart = foodCart,
                        onDeleteClick = {},
                        onSubClick = { foodId, foodSize -> onSubClick(foodId, foodSize) },
                        onAddClick = { foodId, foodSize -> onAddClick(foodId, foodSize) })
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
        if (addToCart) {
            Text(
                text = "Payment method",
                fontWeight = FontWeight.Bold,
                color = DarkGrey,
                fontSize = TextSize.regular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            paymentMethods.forEach { method ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedMethod = method } // Select method on row click
                        .padding(horizontal = 20.dp,vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = (selectedMethod == method),
                        onClick = { selectedMethod = method } // Update state on click
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = method ,color = DarkGrey,
                        fontSize = TextSize.small,)
                }
            }
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
                else{
                    onOrder(foodCartList)
                }
            }
        }
    }
}

@Composable
fun FoodCartList(
    foodCart: FoodCart,
    onDeleteClick: () -> Unit,
    onSubClick: (String, String) -> Unit,
    onAddClick: (String, String) -> Unit
) {
    var viewDetails by remember { mutableStateOf(false) }
    var editable by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.width(300.dp).background(White)
            .padding(vertical = 10.dp, horizontal = 12.dp)
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
                    maxLines = 1,
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
                            viewDetails = !viewDetails
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
                            viewDetails = !viewDetails
                            editable = !editable
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

        if (viewDetails || editable) {
            foodCart.foodCartDetailsList.forEach { foodCartList ->
                if (foodCartList.quantity != 0) {
                    FoodItemRow(foodCartDetail = foodCartList, onSubClick = {
                        if (editable) {
                            if (foodCartList.quantity >= 1) {
                                onSubClick(foodCart.foodId, foodCartList.foodSize)
                            }
                        }
                    }, onAddClick = {
                        if (editable) {
                            onAddClick(foodCart.foodId, foodCartList.foodSize)
                        }
                    }, editable = editable)
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
                        viewDetails = !viewDetails
                    }
                }

                CustomButton(text = if (editable) "Save" else "Edit", buttonColor = Green) {

                }
            }
        }
    }
}

@Composable
fun FoodItemCard(name: String, desc: String, tags: List<String>, isVeg: Boolean,imageBitmap: ImageBitmap?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, LightGrey, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightGrey)
        ){
            imageBitmap?.let {
                Image(bitmap = it, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
            }
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(name, fontWeight = FontWeight.Bold)
            Text(desc, fontSize = 14.sp, color = DarkGrey)
            Row {
                tags.forEach {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(50))
                            .background(LightGrey)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(it, fontSize = 12.sp)
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.Default.Check,
            contentDescription = null,
            tint = if (isVeg) Green else Red
        )
    }
}