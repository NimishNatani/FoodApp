package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.repository.BookingRepository
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingScreenViewModel(private val screenSize: PlatformConfiguration,
                             private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookingScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: BookingScreenAction) {
        when (action) {
            BookingScreenAction.GetOrderList -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
                getOrderList()
            }
            BookingScreenAction.ApiCalling -> {
                _uiState.value = _uiState.value.copy(apiCalling = _uiState.value.apiCalling + 1)
            }
        }
    }

    private fun getOrderList(){
        viewModelScope.launch(Dispatchers.IO) {
            bookingRepository.getOrders().onSuccess { orderList ->
                _uiState.update {
                    it.copy(isLoading = false, orderList = orderList)
                }
            }.onError { error->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = error.toString())
                }
            }
        }

    }

    fun getScreenSize():Pair<Float,Float>{
        return Pair(screenSize.screenWidth(),screenSize.screenHeight())
    }
}