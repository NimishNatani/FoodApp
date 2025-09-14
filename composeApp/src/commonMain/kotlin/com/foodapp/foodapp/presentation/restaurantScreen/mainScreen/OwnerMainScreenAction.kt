package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen

sealed interface OwnerMainScreenAction {
    data class OnTabSelected(val index: Int) : OwnerMainScreenAction
    object RefreshData : OwnerMainScreenAction
    data class OnDataLoaded(
        val restaurants: List<com.foodapp.foodapp.domain.models.Restaurant>,
        val bookings: List<com.foodapp.foodapp.domain.models.Booking>
    ) : OwnerMainScreenAction
}