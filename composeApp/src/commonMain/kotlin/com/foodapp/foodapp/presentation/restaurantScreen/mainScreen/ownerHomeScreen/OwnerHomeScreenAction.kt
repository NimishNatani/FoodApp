package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen

import com.foodapp.foodapp.domain.models.Booking

sealed interface OwnerHomeScreenAction {
    fun loadOrders()                    // initial / refresh
    fun selectOrder(bookingId: String)  // open details sheet
    fun closeDetails()
    fun changeOrderStatus(bookingId: String, newStatus: BookingStatus)
    fun openChat(bookingId: String)
    fun selectBottomTab(index: Int)
    fun selectTopStatus(status: BookingStatus)

    fun RefreshData()
    fun OnBookingClicked( booking: Booking)
    fun ChangeBookingStatus(booking: BookingUi, newStatus: String)
    fun OnChatWithUser(booking: BookingUi)
}