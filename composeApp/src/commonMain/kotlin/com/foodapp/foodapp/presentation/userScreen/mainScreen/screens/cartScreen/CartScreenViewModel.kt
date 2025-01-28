package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.domain.models.FoodCart
import com.foodapp.foodapp.domain.repository.BookingRepository
import com.foodapp.foodapp.domain.repository.UserRepository
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(
    private val userRepository: UserRepository, private val screenSize: PlatformConfiguration,
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CartScreenAction) {
        when (action) {
            is CartScreenAction.GetFoodCartList -> {
                _uiState.update { it.copy(isLoading = true) }
                getFoodCartList()
            }

            is CartScreenAction.OnSubClick -> {
                updateFoodCart(action.foodId, action.foodSize, decrement = true)
            }

            is CartScreenAction.OnAddClick -> {
                updateFoodCart(action.foodId, action.foodSize, decrement = false)
            }

            is CartScreenAction.OnOrder -> {
                _uiState.update { it.copy(isLoading = true) }
                orderFood(action.foodCartList)
            }
        }
    }

    private fun getFoodCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getFoodCart().onSuccess { foodCartList ->
                _uiState.update {
                    it.copy(isLoading = false, cartList = foodCartList)
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = error.toString())
                }
            }
        }
    }

    private fun updateFoodCart(foodId: String, foodSize: String, decrement: Boolean) {
        _uiState.update { currentState ->
            val updatedCartList = currentState.cartList.map { foodCart ->
                if (foodCart.foodId == foodId) {
                    val updatedDetails = foodCart.foodCartDetailsList.map { detail ->
                        if (detail.foodSize == foodSize) {
                            detail.copy(
                                quantity = (detail.quantity + if (decrement) -1 else 1).coerceAtLeast(
                                    0
                                )
                            )
                        } else {
                            detail
                        }
                    }
                    foodCart.copy(
                        foodCartDetailsList = updatedDetails,
                        totalPrice = updatedDetails.sumOf { it.foodPrice * it.quantity }
                    )
                } else {
                    foodCart
                }
            }
            currentState.copy(cartList = updatedCartList)
        }
    }

    private fun orderFood(foodCartList: List<FoodCart>) {
        viewModelScope.launch {
            bookingRepository.saveOrder(booking = Booking(
                bookingId = "",
                userId = "",
                restaurantId = foodCartList.first().restaurantId,
                isAcceptedByRestaurant = false,
                isBookingCompleted = false,
                message = emptyList(),
                foodCarts = foodCartList,
                acceptedTime = 30,
                isPaymentDone = false,
                paymentId = "null",
                reviewList = emptyList(),
                amount = foodCartList.sumOf { it.totalPrice }
            )).onSuccess {
                userRepository.deleteItemFromCart(foodCartList).onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            cartList = uiState.value.cartList.filterNot { cart ->
                                foodCartList.any {foodCart ->  foodCart.foodId == cart.foodId }
                            })
                    }
                }.onError { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toString()
                        )
                    }
                }
            }.onError { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toString()
                    )
                }
            }
        }
    }

    fun getScreenSize(): Pair<Int, Int> {
        return Pair(screenSize.screenWidth(), screenSize.screenHeight())
    }
}