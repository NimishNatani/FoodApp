package com.foodapp.foodapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
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


     fun getUser(onSuccess: () -> Unit,onFailure: () -> Unit) {
        viewModelScope.launch {
            userRepository.getUserByJwttoken().onSuccess {result ->
                println("User fetched successfully: $result")
                _user.value = result
                if (user.value==result){
                    onSuccess()
                }
                println("User: ${user.value}")
            }.onError { onFailure() }

        }
    }

    fun setUser(user:User?){
        _user.value = user
    }
}