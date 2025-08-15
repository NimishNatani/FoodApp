package com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen

import androidx.compose.ui.graphics.ImageBitmap
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenAction

sealed interface FoodDetailScreenAction {

    data object OnImageUploadTrigger: FoodDetailScreenAction
    data class OnImageSelected(val byteArray: ByteArray?): FoodDetailScreenAction
    data class AddNewFood(val food: Food,val imageBitmap: ImageBitmap?):FoodDetailScreenAction
}