package com.foodapp.foodapp.presentation.starter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import com.foodapp.foodapp.presentation.navigation.Route

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        val response =
        delay(2000) // Show splash for 2 seconds
       navController.navigate(Route.UserSelection) {
            popUpTo(Route.SplashScreen) { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "FoodApp", style = MaterialTheme.typography.headlineLarge)
    }
}
