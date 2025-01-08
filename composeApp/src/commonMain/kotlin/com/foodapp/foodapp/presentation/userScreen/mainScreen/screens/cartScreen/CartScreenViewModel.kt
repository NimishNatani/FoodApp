package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.repository.UserRepository
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartScreenViewModel(private val userRepository: UserRepository,private val screenSize: PlatformConfiguration) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CartScreenAction) {
        when (action) {
            is CartScreenAction.GetFoodCartList -> {
                _uiState.update { it.copy(isLoading = true) }
                getFoodCartList()
            }
        }
    }

    private fun getFoodCartList() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getFoodCart().onSuccess { foodCartList ->
                _uiState.update {
                    it.copy(isLoading = false, cartList = foodCartList)
                }
            }.onError { error->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = error.toString())
                }
            }
        }
    }
    fun getScreenSize():Pair<Int,Int>{
        return Pair(screenSize.screenWidth(),screenSize.screenHeight())
    }
}