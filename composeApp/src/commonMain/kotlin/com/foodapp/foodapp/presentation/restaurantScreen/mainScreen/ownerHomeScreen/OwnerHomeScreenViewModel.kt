package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OwnerHomeScreenViewModel(private val repo: BookingRepository): ViewModel(),OwnerHomeScreenAction {  // Backing stateflow
private val _uiState = MutableStateFlow(OwnerHomeScreenState(isLoading = true))
val uiState: StateFlow<OwnerHomeScreenState> = _uiState



override fun loadOrders() {
    viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        try {
            val bookings = repo.getOrder("")

                val uiList = bookings.map {order-> order.toBookingUi() }
                _uiState.value = _uiState.value.copy(isLoading = false, bookings = uiList)

        } catch (t: Throwable) {
            _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = t.message ?: "Unknown")
        }
    }
}

override fun selectOrder(bookingId: String) {
    _uiState.value = _uiState.value.copy(selectedBookingId = bookingId)
}

override fun closeDetails() {
    _uiState.value = _uiState.value.copy(selectedBookingId = null)
}

override fun changeOrderStatus(bookingId: String, newStatus: BookingStatus) {
    viewModelScope.launch(Dispatchers.IO) {
        // optimistic UI: update local state immediately
        val current = _uiState.value
        val updatedList = current.bookings.map { b ->
            if (b.bookingId == bookingId) b.copy(status = newStatus) else b
        }
        _uiState.value = current.copy(bookings = updatedList)

        try {
//            val updatedBooking = repo.updateBookingStatus(bookingId, newStatus)
//            updatedBooking?.let {
//                // map to ui and inject
//                val mapped = it.toBookingUi()
//                val refreshed = _uiState.value.bookings.map { bu -> if (bu.bookingId == bookingId) mapped else bu }
//                _uiState.value = _uiState.value.copy(bookings = refreshed)
//            }
        } catch (t: Throwable) {
            // revert on error + show message
            loadOrders()
            _uiState.value = _uiState.value.copy(errorMessage = t.message ?: "Failed to update")
        }
    }
}

override fun openChat(bookingId: String) {
    // For now, we only set selection; platform can observe events or expose nav single-shot events.
    // Simple: set selectedBookingId so platform can react, or provide a channel for one-shot nav events.
    _uiState.value = _uiState.value.copy(selectedBookingId = bookingId)
}

override fun selectBottomTab(index: Int) {
    _uiState.value = _uiState.value.copy(selectedBottomTabIndex = index)
}

override fun selectTopStatus(status: BookingStatus) {
    _uiState.value = _uiState.value.copy(selectedTopStatus = status)
}

    override fun RefreshData() {
        TODO("Not yet implemented")
    }

    override fun OnBookingClicked(booking: Booking) {
        TODO("Not yet implemented")
    }

    override fun ChangeBookingStatus(
        booking: BookingUi,
        newStatus: String
    ) {
        TODO("Not yet implemented")
    }

    override fun OnChatWithUser(booking: BookingUi) {
        TODO("Not yet implemented")
    }

    // Optional: accessory functions
fun refresh() = loadOrders()
}

/** Mapping helper from Booking domain model to BookingUi */
private fun Booking.toBookingUi(): BookingUi {
    // Derive customer name from userId for demo; ideally you'd fetch user data
    val customerName = this.userId.takeIf { it.isNotBlank() } ?: "Customer"
    val itemsSummary = when {
        this.foodCarts.isNotEmpty() -> {
            this.foodCarts.joinToString(", ") { fc ->
                val qty = fc.foodCartDetailsList.sumOf { it.quantity }
                "${qty}x ${fc.foodName}"
            }
        }
        else -> "No items"
    }
    val amount = this.amount
    val status = when {
        this.isBookingCompleted -> BookingStatus.COMPLETED
        this.isAcceptedByRestaurant -> BookingStatus.ACCEPTED
        else -> BookingStatus.PENDING
    }
    return BookingUi(
        bookingId = this.bookingId,
        customerName = customerName,
        itemsSummary = itemsSummary,
        amount = amount,
        status = status,
        booking = this
    )
}