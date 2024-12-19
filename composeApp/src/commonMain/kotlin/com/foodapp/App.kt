package com.foodapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.foodapp.core.presentation.Red
import com.foodapp.foodapp.presentation.RestaurantViewModel
import com.foodapp.foodapp.presentation.UserViewModel
import com.foodapp.foodapp.presentation.login.AuthLoginViewModel
import com.foodapp.foodapp.presentation.login.LoginScreenRoot
import com.foodapp.foodapp.presentation.navigation.Route
import com.foodapp.foodapp.presentation.register.AuthRegisterViewModel
import com.foodapp.foodapp.presentation.register.RegisterScreenRoot
import com.foodapp.foodapp.presentation.starter.AuthValidationViewModel
import com.foodapp.foodapp.presentation.starter.SplashScreen
import com.foodapp.foodapp.presentation.starter.UserSelectionScreen
import com.foodapp.foodapp.sharedObjects.SharedObject.sharedUser
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

                val viewModel: AuthValidationViewModel = koinViewModel<AuthValidationViewModel>()
                val sharedUserViewModel = it.sharedKoinViewModel<UserViewModel>(navController)
                val sharedRestaurantViewModel =
                    it.sharedKoinViewModel<RestaurantViewModel>(navController)

                SplashScreen(
                    viewModel,
                    onResponse = {
                        if (sharedUser == true) {
                            sharedUserViewModel.getUser(onSuccess = { navController.navigate(Route.UserHomeScreen) { navController.popBackStack() } },
                                onFailure = { navController.navigate(Route.UserSelection) { navController.popBackStack() } })
                        } else if (sharedUser == false) {
                            sharedRestaurantViewModel.getRestaurant(onSuccess = {
                                navController.navigate(
                                    Route.RestaurantHomeScreen
                                ) { navController.popBackStack() }
                            },
                                onFailure = { navController.navigate(Route.UserSelection) { navController.popBackStack() } })
                        } else {
                            navController.navigate(Route.UserSelection)
                        }
                    },
                    navController
                )
            }

            // User Selection Screen (User or Restaurant)
            composable<Route.UserSelection> {
                UserSelectionScreen(onUserSelected = {
                    navController.navigate(
                        Route.Login
                    )
                })
            }

            // Register Screen
            composable<Route.Register> {
                val viewModel: AuthRegisterViewModel = koinViewModel()

                if (sharedUser != null) {
                    RegisterScreenRoot(
                        viewModel,
                        onRegisterClicked = { navController.navigate(Route.Login) },
                        isUser = sharedUser!!,
                        onLogin = {navController.navigate(Route.Login){navController.popBackStack()} }
                    )
                } else {
                    navController.navigate(Route.UserSelection) {
                        navController.popBackStack()
                    }
                }
            }

            // Login Screen
            composable<Route.Login> {
                val viewModel: AuthLoginViewModel = koinViewModel()

                if (sharedUser != null) {
                    LoginScreenRoot(
                        viewModel = viewModel,
                        onLoginClicked = {},
                        isUser = sharedUser!!,
                        onSignUp = {navController.navigate(Route.Register){navController.popBackStack()} }
                    )
                } else {
                    navController.navigate(Route.UserSelection) {
                        navController.popBackStack()
                    }
                }
            }

            composable<Route.UserHomeScreen> {
                val sharedUserViewModel = it.sharedKoinViewModel<UserViewModel>(navController)
                Text(text = sharedUserViewModel.user.value.toString(), color = Red)
            }
            composable<Route.RestaurantHomeScreen> {
                val sharedRestaurantViewModel = it.sharedKoinViewModel<RestaurantViewModel>(navController)
                Text(text = sharedRestaurantViewModel.restaurant.value.toString(), color = Red)
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}