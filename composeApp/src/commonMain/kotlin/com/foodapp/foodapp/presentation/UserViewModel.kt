package com.foodapp.foodapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _getCityRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val getCityRestaurants = _getCityRestaurants.asStateFlow()

    private val _restaurant = MutableStateFlow<Restaurant?>(null)
    val restaurant = _restaurant.asStateFlow()

    private val _food = MutableStateFlow<Food?>(null)
    val food = _food.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Pair<DrawableResource,String>?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    fun setUser(user: User) {
        _user.value = user
    }


    fun getUser(onFailure: () -> Unit) {
        viewModelScope.launch {
            userRepository.getUserByJwttoken().onSuccess { result ->
                println("User fetched successfully: $result")
                _user.value = result
                println("User: ${user.value}")
            }.onError { onFailure() }

        }
    }

    fun setListRestaurants(list: List<Restaurant>) {
        _getCityRestaurants.value = list
    }

    fun setRestaurant(restaurant: Restaurant) {
        _restaurant.value = restaurant
    }

    fun setCategory(category: Pair<DrawableResource,String>) {
        _selectedCategory.value = category

    }

    fun setFood(food: Food) {
        _food.value = food
    }


}