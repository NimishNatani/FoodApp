package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen

import androidx.lifecycle.ViewModel
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.models.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class OwnerMainScreenViewModel: ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _uiState = MutableStateFlow(OwnerMainScreenState())
    val uiState: StateFlow<OwnerMainScreenState> = _uiState

    fun onAction(action: OwnerMainScreenAction) {
        when (action) {
            is OwnerMainScreenAction.OnTabSelected -> {
                _uiState.update { it.copy(selectedTabIndex = action.index) }
            }

            is OwnerMainScreenAction.RefreshData -> {
                refreshData()
            }

            is OwnerMainScreenAction.OnDataLoaded -> {
                _uiState.update {
                    it.copy(
                        restaurantData = action.restaurants,
                        bookingData = action.bookings,
                        isRefreshing = false,
                        lastUpdated = Clock.System.now().toEpochMilliseconds()
                    )
                }
            }
        }
    }

    private fun refreshData() {
        _uiState.update { it.copy(isRefreshing = true) }

        viewModelScope.launch {
            // 🔹 Fake backend call (replace with real repo call)
            kotlinx.coroutines.delay(2000)

            val mockRestaurants = listOf(
                Restaurant(
                    restaurantId = "1",
                    restaurantName = "My Cafe",
                    restaurantImage = "",
                    contactDetails = "9876543210",
                    latitude = 26.45,
                    longitude = 74.63,
                    address = "Ajmer",
                    city = "Ajmer",
                    state = "Rajasthan",
                    postelCode = "305001",
                    restaurantTags = listOf("Cafe", "Snacks")
                )
            )
            val mockBookings = emptyList<Booking>()

            onAction(OwnerMainScreenAction.OnDataLoaded(mockRestaurants, mockBookings))
        }
    }
}
