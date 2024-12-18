package com.foodapp.foodapp.presentation.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val result:String = "",
    var isUser:Boolean=true,
    val message:String?=null

)
