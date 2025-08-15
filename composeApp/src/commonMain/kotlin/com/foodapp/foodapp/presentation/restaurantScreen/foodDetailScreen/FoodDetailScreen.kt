package com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import com.foodapp.foodapp.presentation.components.FlowRow
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodapp.core.presentation.*
import com.foodapp.foodapp.di.PickImage
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.FoodDetails
import com.foodapp.foodapp.presentation.components.CustomTextField
import com.foodapp.foodapp.presentation.components.CustomButton
import com.foodapp.foodapp.presentation.components.DropdownMenuBox
import com.foodapp.foodapp.presentation.components.FoodItemCard
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenAction
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@Composable
fun FoodDetailScreenRoot(viewModel: FoodDetailScreenViewModel){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    FoodDetailScreen(state = state,onEvent = { event ->
        viewModel.onAction(event)
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(state: FoodDetailScreenState,onEvent: (FoodDetailScreenAction) -> Unit) {
    if (state.isLoading) {
        Text("Loading")
    } else if (!state.isLoading && state.errorMessage != null) {
        Text(state.errorMessage)
    } else {


        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        // --- Form state ---
        var foodName by remember { mutableStateOf("") }
        var foodDescription by remember { mutableStateOf("") }
        var isVeg by remember { mutableStateOf(true) }

        var tagInput by remember { mutableStateOf("") }
        var foodTags by remember { mutableStateOf(listOf<String>()) }

        var foodImageUrl by remember { mutableStateOf("") }

        // Size + Price (uses FoodDetails instead of Pair)
        var sizeSelected by remember { mutableStateOf("Small") }
        var sizePrice by remember { mutableStateOf("") }
        var foodDetails by remember { mutableStateOf(listOf<FoodDetails>()) }

        var imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }


        fun resetForm() {
            foodName = ""
            foodDescription = ""
            isVeg = true
            tagInput = ""
            foodTags = emptyList()
            foodImageUrl = ""
            sizeSelected = "Small"
            sizePrice = ""
            foodDetails = emptyList()
            imageBitmap.value = null
        }

        if (state.imageUploadTrigger) {
            PickImage { (bitmap, byteArray) ->
                imageBitmap.value = bitmap
                onEvent(FoodDetailScreenAction.OnImageSelected(byteArray))
                onEvent(FoodDetailScreenAction.OnImageUploadTrigger) // reset trigger
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            // Current food list
            val listState = rememberLazyListState()
            Column(Modifier.fillMaxSize()) {
                Text(
                    "Current Food",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(state = listState, contentPadding = PaddingValues(bottom = 96.dp)) {
                    items(state.foodList!!) { f ->
                        FoodItemCard(
                            name = f.first.foodName,
                            desc = f.first.foodDescription,
                            tags = f.first.foodTags,
                            isVeg = f.first.isVeg,
                            imageBitmap = f.second
                        )
                    }
                }
            }

            // FAB
            FloatingActionButton(
                onClick = { scope.launch { sheetState.show() } },
                containerColor = Green,
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Food", tint = White)
            }

            // Bottom Sheet - Add Food
            if (sheetState.isVisible || sheetState.targetValue != SheetValue.Hidden) {
                ModalBottomSheet(
                    onDismissRequest = { scope.launch { sheetState.hide() } },
                    sheetState = sheetState,
                    containerColor = White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    Column(Modifier.fillMaxWidth().padding(16.dp).verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally,) {
                        Text("Add New Food", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                        CustomTextField("Food Name", foodName, { foodName = it }, type = "text")
                        CustomTextField(
                            label = "Food Description",
                            value = foodDescription,
                            onValueChange = { foodDescription = it },
                            type = "text"
                        )

                        Spacer(Modifier.height(8.dp))

                        // Veg/Non-Veg toggle via check icons
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                            Text("Type:", Modifier.padding(end = 8.dp))
                            Text(
                                text = "isVeg",
                                color = if (isVeg) Green else DarkGrey,
                                modifier = Modifier.size(35.dp).clickable { isVeg = true }
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = "nonVeg",
                                color = if (!isVeg) Red else DarkGrey,
                                modifier = Modifier.size(50.dp).clickable { isVeg = false }
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        // --- Size + Price row ---
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            DropdownMenuBox(
                                label = "Size",
                                options = listOf("Small", "Medium", "Large", "Half", "Full"),
                                selected = sizeSelected,
                                onSelect = { sizeSelected = it },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(Modifier.width(8.dp))
                            OutlinedTextField(
                                value = sizePrice,
                                onValueChange = {
                                    sizePrice = it.filter { ch -> ch.isDigit() || ch == '.' }
                                },
                                label = { Text("Price") },
                                modifier = Modifier.width(100.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(Modifier.width(8.dp))
                            CustomButton(text = "Add", onClick = {
                                val p = sizePrice.toDoubleOrNull()
                                if (p != null) {
                                    val idx =
                                        foodDetails.indexOfFirst { it.foodSize == sizeSelected }
                                    foodDetails = if (idx >= 0) {
                                        // replace price for existing size
                                        foodDetails.toMutableList()
                                            .also { it[idx] = FoodDetails(sizeSelected, p) }
                                    } else {
                                        foodDetails + FoodDetails(sizeSelected, p)
                                    }
                                    sizePrice = ""
                                }
                            })
                        }

                        // Render added size-price items
                        Column(Modifier.padding(top = 6.dp)) {
                            foodDetails.forEachIndexed { index, fd ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "${fd.foodSize} - ₹${fd.foodPrice}",
                                        fontSize = 14.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(onClick = {
                                        foodDetails =
                                            foodDetails.toMutableList().also { it.removeAt(index) }
                                    }) { Icon(Icons.Default.Delete, contentDescription = "Remove") }
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Tags
                        Text("Tags", fontWeight = FontWeight.SemiBold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CustomTextField("Add Tag", tagInput, { tagInput = it }, type = "text",modifier = Modifier.weight(1f))
                            Spacer(Modifier.width(8.dp))
                            CustomButton(text = "Add",onClick = {
                                val t = tagInput.trim()
                                if (t.isNotEmpty() && t !in foodTags) {
                                    foodTags = foodTags + t
                                    tagInput = ""
                                }
                            })
                        }
                        FlowRow(

                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            foodTags.forEach { tag ->
                                TagPill(
                                    text = tag,
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        // Image picker placeholder
                        Box(
                            modifier = Modifier.fillMaxWidth().height(150.dp)
                                .clip(RoundedCornerShape(10.dp)).background(LightGrey).clickable {
                                    onEvent(FoodDetailScreenAction.OnImageUploadTrigger)
                            },
                            contentAlignment = Alignment.Center
                        ) {
                            imageBitmap.value?.let {
                                Image(bitmap = it, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit)
                            } ?: Text("Upload Image")
                        }

                        Spacer(Modifier.height(16.dp))

                        // Save
                        CustomButton("Save Food") {
                            val newFood = Food(
                                foodId = "food_",
                                foodName = foodName.trim(),
                                foodDescription = foodDescription.trim(),
                                foodTags = foodTags,
                                foodImage = foodImageUrl,
                                foodDetails = foodDetails,
                                isAvailable = true,
                                isVeg = isVeg,
                                foodType = listOf(if (isVeg) "Veg" else "NonVeg"),
                                rating = null,
                                totalReviews = null,
                                restaurantId = "REPLACE_WITH_ID",
                                restaurantName = "",
                                transitionTag = ""
                            )
                            onEvent(FoodDetailScreenAction.AddNewFood(newFood,imageBitmap.value))
                            scope.launch { sheetState.hide() }
                            resetForm()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TagPill(text: String) {
    Box(
        modifier = Modifier.padding(2.dp).clip(RoundedCornerShape(50)).background(LightGrey).padding(horizontal = 8.dp, vertical = 4.dp)
    ) { Text(text, fontSize = 15.sp) }
}