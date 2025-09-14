package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen

import com.foodapp.foodapp.domain.models.Booking


enum class BookingStatus { PENDING, ACCEPTED, COMPLETED }
data class OwnerHomeScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedBottomTabIndex: Int = 0,
    val selectedTopStatus: BookingStatus = BookingStatus.PENDING,
    val bookings: List<BookingUi> = emptyList(),
    val selectedBookingId: String? = null, // currently opened sheet
    var isRefreshing: Boolean = false,
    val lastUpdatedFormatted: String? = null,       // ex: "12:45 PM"

)
data class BookingUi(
    val bookingId: String,
    val customerName: String,
    val itemsSummary: String,
    val amount: Double?,
    val status: BookingStatus,
    val booking: Booking? = null,
)