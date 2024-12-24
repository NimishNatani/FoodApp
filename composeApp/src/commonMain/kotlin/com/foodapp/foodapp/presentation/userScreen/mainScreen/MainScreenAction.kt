package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.ui.graphics.vector.ImageVector
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

sealed interface MainScreenAction {

    data class OnTabSelected(val index: Int,val icon:ImageVector): MainScreenAction

}