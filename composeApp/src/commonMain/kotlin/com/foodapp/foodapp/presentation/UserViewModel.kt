package com.foodapp.foodapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.domain.repository.UserRepository
import com.foodapp.foodapp.storage.TokenStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _getCityRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val getCityRestaurants = _getCityRestaurants.asStateFlow()

    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant = _restaurant.asStateFlow()

    fun setUser(user: User){
        _user.value = user
    }


    fun getUser(onFailure: () -> Unit) {
        viewModelScope.launch {
            userRepository.getUserByJwttoken().onSuccess {result ->
                println("User fetched successfully: $result")
                _user.value = result
                println("User: ${user.value}")
            }.onError { onFailure() }

        }
    }

    fun setListRestaurants(list: List<Restaurant>){
        _getCityRestaurants.value = list
    }

    fun setRestaurant(restaurant: Restaurant){
        _restaurant.value = restaurant
    }


}