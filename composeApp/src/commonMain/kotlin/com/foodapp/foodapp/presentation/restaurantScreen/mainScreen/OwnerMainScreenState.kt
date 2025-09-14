package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen

import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.models.Restaurant


data class OwnerMainScreenState(
    val selectedTabIndex: Int = 0,
    val isRefreshing: Boolean = false,
    val lastUpdated: Long? = null,
    val restaurantData: List<Restaurant> = emptyList(),
    val bookingData: List<Booking> = emptyList()
)

