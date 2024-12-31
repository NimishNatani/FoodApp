package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.presentation.components.FoodCart
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreenRoot(cartScreenViewModel: CartScreenViewModel = koinViewModel()) {
    val state by cartScreenViewModel.uiState.collectAsStateWithLifecycle()
    CartScreen(state = state, onBackClick = {})

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(state: CartScreenState, onBackClick: () -> Unit) {
    if (state.isLoading) {
        Text("Loading")
    } else if (!state.isLoading && state.errorMessage != null) {
        Text(state.errorMessage)
    } else {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        TopAppBar(
            title = {
                Text(
                    text = "Cart",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSize.large
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = DarkGrey,
                    contentDescription = "arrow",
                    modifier = Modifier.padding(start = 4.dp).clickable { onBackClick() }
                )
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = White
            )
        )

        val groupedFoodCarts = state.cartList.groupBy { it.restaurantId }.values.toList()

        groupedFoodCarts.forEach { groupedList ->
            FoodCart(foodCartList = groupedList)
        }
    }
    }
}