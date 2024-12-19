package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.foodapp.data.dto.CheckUser
import com.foodapp.foodapp.domain.models.AuthToken
import com.foodapp.foodapp.domain.models.ValidateUser

fun AuthResponse.toAuthToken():AuthToken{
    return AuthToken(
        token=jwt,
        message = message
    )
}

fun CheckUser.toValidateUser(): ValidateUser {
    return ValidateUser(
        isUser = userType

    )
}