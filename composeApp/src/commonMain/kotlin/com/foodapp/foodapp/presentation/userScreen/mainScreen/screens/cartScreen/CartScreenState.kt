package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import com.foodapp.foodapp.domain.models.FoodCart

data class CartScreenState(
    var cartList: List<FoodCart> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)
