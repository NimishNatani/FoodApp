package com.foodapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.foodapp.foodapp.di.initKoin
import com.foodapp.foodapp.presentation.login.AuthLoginViewModel
import com.foodapp.foodapp.presentation.login.LoginScreenRoot
import com.foodapp.foodapp.presentation.navigation.Route
import com.foodapp.foodapp.presentation.register.AuthRegisterViewModel
import com.foodapp.foodapp.presentation.register.RegisterScreenRoot
import com.foodapp.foodapp.presentation.starter.SplashScreen
import com.foodapp.foodapp.presentation.starter.UserSelectionScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Route.SplashScreen
        ) {
            // Splash Screen
            composable<Route.SplashScreen> {
                SplashScreen(navController)
            }

            // User Selection Screen (User or Restaurant)
            composable<Route.UserSelection> {
                UserSelectionScreen(onUserSelected = {navController.navigate(Route.Register.createRoute(isUser  = it))})
            }

            // Register Screen
            composable(
                route = Route.Register.route,
                arguments = listOf(navArgument("isUser") { type = NavType.BoolType })
            ) { backStackEntry ->
                val isUser = backStackEntry.arguments?.getBoolean("isUser") ?: true
                val viewModel:AuthRegisterViewModel = koinViewModel()

                RegisterScreenRoot(viewModel,
                    onRegisterClicked = {navController.navigate(Route.Login.createRoute(isUser=isUser))  }, isUser=isUser
                )
            }

            // Login Screen
            composable(Route.Login.route,
                arguments = listOf(navArgument("isUser") { type = NavType.BoolType })
            ) {backStackEntry ->
                val isUser = backStackEntry.arguments?.getBoolean("isUser") ?: true
                val viewModel:AuthLoginViewModel = koinViewModel()

                LoginScreenRoot(viewModel = viewModel, onLoginClicked = {},isUser = isUser)
            }
        }
    }
}