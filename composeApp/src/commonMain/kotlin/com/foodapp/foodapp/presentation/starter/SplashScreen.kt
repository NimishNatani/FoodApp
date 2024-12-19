package com.foodapp.foodapp.presentation.starter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import com.foodapp.core.domain.onError
import com.foodapp.core.domain.onSuccess
import com.foodapp.foodapp.presentation.UserViewModel
import com.foodapp.foodapp.presentation.navigation.Route
import com.foodapp.foodapp.sharedObjects.SharedObject.sharedUser

@Composable
fun SplashScreen(viewModel: AuthValidationViewModel,onResponse:() ->Unit, navController: NavController) {
    LaunchedEffect(Unit) {
        val response =viewModel.validateUser()
       response.onSuccess {
           when (it.isUser) {
               "user" -> {
                   sharedUser=true
                   onResponse()
               }
               "restaurant" -> {
                   sharedUser= false
                   onResponse()
               }
               else -> {
                   navController.navigate(Route.UserSelection){
                       navController.popBackStack()
                   }
               }
           }
       }
        response.onError {error->

                navController.navigate(Route.UserSelection){
                    navController.popBackStack()

            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "FoodApp", style = MaterialTheme.typography.headlineLarge)
    }
}
