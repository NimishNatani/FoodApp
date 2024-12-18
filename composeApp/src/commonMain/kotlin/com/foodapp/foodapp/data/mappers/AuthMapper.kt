package com.foodapp.foodapp.data.mappers

import com.foodapp.foodapp.data.dto.AuthResponse
import com.foodapp.foodapp.domain.models.AuthToken

fun AuthResponse.toAuthToken():AuthToken{
    return AuthToken(
        token=jwt,
        message = message
    )
}