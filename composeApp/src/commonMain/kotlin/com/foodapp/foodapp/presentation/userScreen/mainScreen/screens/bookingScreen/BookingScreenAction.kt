package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen

import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen.CartScreenAction

sealed interface BookingScreenAction {

    data object GetOrderList : BookingScreenAction

}