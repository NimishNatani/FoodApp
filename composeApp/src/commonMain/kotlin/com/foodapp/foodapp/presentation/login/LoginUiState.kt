package com.foodapp.foodapp.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    var isLoading: Boolean = false,
    val errorMessage: String? = null,
    val result:String = "",
    var isUser:Boolean=true,
    val message:String?=null

)
