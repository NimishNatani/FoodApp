package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen

import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.models.FoodCart

data class BookingScreenState (
    val orderList: List<Booking> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val apiCalling:Int = 0
)