package com.foodapp.foodapp.presentation.starter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.DataError
import com.foodapp.core.domain.Result
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.domain.models.User
import com.foodapp.foodapp.domain.models.ValidateUser
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import com.foodapp.foodapp.domain.repository.UserRepository
import com.foodapp.foodapp.storage.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthValidationViewModel(private val authRepository: AuthRepository):ViewModel() {



//    private val _restaurant = MutableStateFlow<Restaurant?>(null)
//    val restaurant = _restaurant.asStateFlow()
//
//    private val _user = MutableStateFlow<User?>(null)
//    val user = _user.asStateFlow()






    suspend fun validateUser(): Result<ValidateUser, DataError.Remote> {
        return authRepository.validateUser()
    }
}