package com.foodapp.foodapp.presentation.restaurantScreen.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.LightGrey
import com.foodapp.foodapp.di.PickImage
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.components.CustomButton
import com.foodapp.foodapp.presentation.components.CustomTextField
import com.foodapp.foodapp.presentation.components.TagPickerDropdown

@Composable
fun DetailScreenRoot(viewModel: DetailScreenViewModel, restaurant: Restaurant,onSuccess: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.addRestaurant(restaurant)
    }
    state.restaurant?.let {
        DetailScreen(state = state,
            onEvent = { event ->
                viewModel.onAction(event)
            },
            onSuccess = {onSuccess()}
        )
    }
}

@Composable
fun DetailScreen(
    state: DetailScreenState,
    onEvent: (DetailScreenAction) -> Unit,
    onSuccess:() ->Unit
) {
    if (state.isLoading) {
        Text("Loading")
    }else if(state.success){
        onSuccess()
    } else if (!state.isLoading && state.errorMessage != null) {
        Text(state.errorMessage)
    } else if (state.restaurant != null) {
        val imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
        if (state.imageUploadTrigger) {
            PickImage { (bitmap, byteArray) ->
                imageBitmap.value = bitmap
                onEvent(DetailScreenAction.OnImageSelected(byteArray))
                onEvent(DetailScreenAction.OnImageUploadTrigger) // reset trigger
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image Circle
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, LightGrey, CircleShape)
                    .clickable {
                        onEvent(DetailScreenAction.OnImageUploadTrigger)
                    },
                contentAlignment = Alignment.Center
            ) {
                imageBitmap.value?.let {
                    Image(bitmap = it, contentDescription = null, modifier = Modifier.fillMaxSize())
                } ?: Text("Upload Image")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Text Fields
            CustomTextField(
                "Restaurant Name",
                state.restaurant!!.restaurantName,
                { onEvent(DetailScreenAction.OnNameChanged(it)) },
                "text"
            )
            CustomTextField(
                "Contact Detail",
                state.restaurant!!.contactDetails!!,
                { onEvent(DetailScreenAction.OnContactChanged(it)) },
                "text"
            )
            CustomTextField(
                "Address",
                state.restaurant!!.address!!,
                { onEvent(DetailScreenAction.OnAddressChanged(it)) },
                "text"
            )
            CustomTextField("City", state.restaurant!!.city!!, type = "text", isEnabled = false)
            CustomTextField("State", state.restaurant!!.state!!, type = "text", isEnabled = false)
            CustomTextField(
                "Postal Code",
                state.restaurant!!.postelCode!!,
                type = "text",
                isEnabled = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tags Multi-Selector
            var selectedTags by remember { mutableStateOf(emptyList<String>()) }
            val allTags = listOf("Indian", "Chinese", "South Indian", "North Indian", "Punjabi", "Gujarati", "Rajasthani", "Maharashtrian", "Bengali", "Kashmiri", "Thai", "Italian", "Continental", "Mexican", "Street Food", "Biryani", "Tandoori", "Chaat", "Dosa", "Idli", "Paneer Dishes", "Dal Curries", "Paratha", "Vegetarian", "Vegan", "Organic", "Healthy Food", "Fast Food", "Desserts", "Beverages"
            )

            TagPickerDropdown(
                allTags = allTags,
                selected = selectedTags,
                onTagToggled = { tag ->

                    selectedTags = if (selectedTags.contains(tag)) {
                        selectedTags - tag
                    } else {
                        selectedTags+ tag
                    }
                    onEvent(DetailScreenAction.OnTagsChanged(selectedTags))
                },
                onClearAll = { selectedTags = emptyList() },
                onAdd = { /* submit or open dialog */ }
            )



            CustomButton(
                text = "Add Restaurant",
                onClick = { onEvent(DetailScreenAction.OnAddRestaurant)}
            )
        }
    }
}