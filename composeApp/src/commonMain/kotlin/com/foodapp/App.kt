package com.foodapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.ViewAllCategoryScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen.ViewRestaurantScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen.ViewRestaurantScreenViewModel
import com.foodapp.foodapp.sharedObjects.SharedObject.sharedUser
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {

    MaterialTheme {
        val navController = rememberNavController()


        NavHost(
            navController = navController,
            startDestination = Route.AuthGraph
        ) {
            navigation<Route.AuthGraph>(
                startDestination = Route.SplashScreen,

                ) {
                // Splash Screen
                composable<Route.SplashScreen> {

                    val viewModel: AuthValidationViewModel =
                        koinViewModel<AuthValidationViewModel>()

                    SplashScreen(
                        viewModel,
                        onResponse = { navigationFunction(navController) },
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
                    val authViewModel: AuthValidationViewModel = koinViewModel()

                    if (sharedUser != null) {
                        RegisterScreenRoot(
                            viewModel,
                            onRegisterClicked = {
                                navigationFunction(
                                    navController
                                )
                            },
                            isUser = sharedUser!!,
                            onLogin = { navController.navigate(Route.Login) { navController.popBackStack() } }
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
                    val authViewModel: AuthValidationViewModel = koinViewModel()

                    if (sharedUser != null) {
                        LoginScreenRoot(
                            viewModel = viewModel,
                            onLoginClicked = { navigationFunction(navController) },
                            isUser = sharedUser!!,
                            onSignUp = { navController.navigate(Route.Register) { navController.popBackStack() } }
                        )
                    } else {
                        navController.navigate(Route.UserSelection) {
                            navController.popBackStack()
                        }
                    }
                }
            }
            navigation<Route.UserGraph>(
                startDestination = Route.UserHomeScreen,
            ) {
                composable<Route.UserHomeScreen> {
                    val userMainScreenViewModel = koinViewModel<UserMainScreenViewModel>()
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)
                    LaunchedEffect(Unit) {
                        sharedUserViewModel.getUser(onFailure = {
                            navController.navigate(Route.UserSelection) {
                                navController.popBackStack(Route.UserSelection, false)
                            }
                        })
                    }
                    val user by sharedUserViewModel.user.collectAsStateWithLifecycle()
                    UserMainScreenRoot(
                        userMainScreenViewModel,
                        onViewAllRestaurantScreen = { restaurants ->
                            sharedUserViewModel.setListRestaurants(restaurants)
                            navController.navigate(Route.RestaurantHomeScreen)
                        },
                        onViewAllCategoryScreen = { restaurants ->
                            sharedUserViewModel.setListRestaurants(restaurants)
                            navController.navigate(Route.ViewAllCategoryScreen)
                        })

                }
                composable<Route.ViewAllRestaurantScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)
                }
                composable<Route.ViewAllCategoryScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)

                    ViewAllCategoryScreenRoot(restaurants = sharedUserViewModel.getCityRestaurants.value,
                        onRestaurantClick = { restaurant ->
                            sharedUserViewModel.setRestaurant(restaurant)
                            navController.navigate(Route.ViewRestaurantScreen)
                        })
                }
                composable<Route.ViewRestaurantScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)
                    val viewModel = koinViewModel<ViewRestaurantScreenViewModel>()

                    sharedUserViewModel.restaurant.value?.let { restaurant -> ViewRestaurantScreenRoot(viewModel = viewModel,restaurant = restaurant) }
                }
            }
            navigation<Route.RestaurantGraph>(
                startDestination = Route.RestaurantHomeScreen
            ) {
                composable<Route.RestaurantHomeScreen> {
                    val sharedRestaurantViewModel =
                        it.sharedKoinViewModel<RestaurantViewModel>(navController)

                    sharedRestaurantViewModel.getRestaurant(onFailure = {
                        navController.navigate(Route.UserSelection) {
                            navController.popBackStack(Route.UserSelection, false)
                        }
                    })


                    Text(
                        text = sharedRestaurantViewModel.restaurant.value.toString(),
                        color = Red
                    )
                }
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

inline fun <reified T : Any> NavBackStackEntry.toRoute(): T {
    val json = arguments?.getString("data") ?: throw IllegalArgumentException("Missing argument")
    return Json.decodeFromString(json)
}


private fun navigationFunction(navController: NavController) {
    when (sharedUser) {
        true -> {
            navController.navigate(Route.UserGraph) {
                navController.popBackStack(Route.UserGraph, false)
            }
        }

        false -> {
            navController.navigate(Route.RestaurantGraph) {
                navController.popBackStack(Route.RestaurantGraph, false)
            }
        }

        else -> {
            navController.navigate(Route.UserSelection)
        }
    }
}