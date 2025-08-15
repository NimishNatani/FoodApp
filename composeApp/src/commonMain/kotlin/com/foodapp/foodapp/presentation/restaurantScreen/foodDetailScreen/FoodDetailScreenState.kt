package com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen

import androidx.compose.ui.graphics.ImageBitmap
import com.foodapp.foodapp.domain.models.Food

data class FoodDetailScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val success:Boolean = false,
    val imageByte: ByteArray? = null,
    val foodList: List<Pair<Food, ImageBitmap?>>? = emptyList(),
    val imageUploadTrigger:Boolean = false,

    )
