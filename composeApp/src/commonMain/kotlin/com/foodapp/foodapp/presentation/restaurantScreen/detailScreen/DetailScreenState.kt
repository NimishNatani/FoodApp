package com.foodapp.foodapp.presentation.restaurantScreen.detailScreen

import androidx.compose.ui.graphics.ImageBitmap
import com.foodapp.foodapp.domain.models.Restaurant

data class DetailScreenState(
    var restaurant: Restaurant? =null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUploadTrigger:Boolean = false,
    val imageByte: ByteArray? = null
)
