package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.foodapp.core.presentation.UiText
import com.foodapp.foodapp.domain.models.Food
import com.foodapp.foodapp.domain.models.Restaurant

data class MainScreenState (
    var selectedTabIndex: BottomNavItem = BottomNavItem(0,Icons.Default.Home),
)


