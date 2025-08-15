@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.foodapp

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
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
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenRoot
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenViewModel
import com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen.FoodDetailScreenRoot
import com.foodapp.foodapp.presentation.restaurantScreen.foodDetailScreen.FoodDetailScreenViewModel
import com.foodapp.foodapp.presentation.starter.AuthValidationViewModel
import com.foodapp.foodapp.presentation.starter.SplashScreen
import com.foodapp.foodapp.presentation.starter.UserSelectionScreen
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen.ViewFoodScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen.ViewFoodScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.ViewAllCategoryScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen.ViewRestaurantScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen.ViewRestaurantScreenViewModel
import com.foodapp.foodapp.sharedObjects.SharedObject.sharedUser
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {

    SharedTransitionLayout {
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
                    sharedUserViewModel.user.value?.let {
                        UserMainScreenRoot(
                            userMainScreenViewModel,
                            onViewAllRestaurantScreen = { restaurants ->
                                sharedUserViewModel.setListRestaurants(restaurants)
                                navController.navigate(Route.RestaurantHomeScreen)
                            },
                            onViewAllCategoryScreen = { restaurants ->
                                sharedUserViewModel.setListRestaurants(restaurants)
                                navController.navigate(Route.ViewAllCategoryScreen)
                            },
                            onViewCategoryItem = { string ->
                                sharedUserViewModel.setCategory(string)
                                navController.navigate(Route.ViewAllCategoryScreen)
                            },
                            onRestaurantClick = { restaurant ->
                                sharedUserViewModel.setRestaurant(restaurant)
                                navController.navigate(Route.ViewRestaurantScreen)
                            },
                            onFoodSelected = { food, restaurant ->
                                sharedUserViewModel.setFood(food)
                                sharedUserViewModel.setRestaurant(restaurant)
                                navController.navigate(Route.ViewFoodScreen)
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                        )
                    }

                }
                composable<Route.ViewAllRestaurantScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)
                }
                composable<Route.ViewAllCategoryScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)

                    ViewAllCategoryScreenRoot(restaurants = sharedUserViewModel.getCityRestaurants.value,
                        category = sharedUserViewModel.selectedCategory.value
                            ?: Pair(Res.drawable.compose_multiplatform, "Indian"),
                        onRestaurantClick = { restaurant ->
                            sharedUserViewModel.setRestaurant(restaurant)
                            navController.navigate(Route.ViewRestaurantScreen)
                        }, sharedTransitionScope = this@SharedTransitionLayout, animatedContentScope = this@composable, onBackClick = { navController.popBackStack() })
                }
                composable<Route.ViewRestaurantScreen> {
                    val sharedUserViewModel =
                        it.sharedKoinViewModel<UserViewModel>(navController)
                    val viewModel = koinViewModel<ViewRestaurantScreenViewModel>()
                        sharedUserViewModel.restaurant.value?.let { restaurant ->
                            ViewRestaurantScreenRoot(viewModel = viewModel,
                                restaurant = restaurant,
                                onFoodClick = { food ->
                                    sharedUserViewModel.setFood(food)
                                    navController.navigate(Route.ViewFoodScreen)
                                },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                                onBackClick = { navController.popBackStack() })
                        }

                }
                composable<Route.ViewFoodScreen> {
                    val sharedUserViewModel = it.sharedKoinViewModel<UserViewModel>(navController)
                    val viewModel = koinViewModel<ViewFoodScreenViewModel>()
                    sharedUserViewModel.food.value?.let { food ->
                        sharedUserViewModel.restaurant.value?.let { restaurant ->
                            println("Food: ${sharedUserViewModel.food.value}")
                            println("Restaurant: ${sharedUserViewModel.restaurant}")
                            ViewFoodScreenRoot(
                                viewModel = viewModel,
                                food = food,
                                restaurantName = restaurant!!.restaurantName,
                                onBackClick = { navController.popBackStack() },
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedContentScope = this@composable,
                            )
                        }
                    }
                }
            }
            navigation<Route.RestaurantGraph>(
                startDestination = Route.RestaurantHomeScreen
            ) {
                composable<Route.RestaurantDetailsScreen>{
                    val sharedRestaurantViewModel =
                        it.sharedKoinViewModel<RestaurantViewModel>(navController)
                    val viewModel = koinViewModel<DetailScreenViewModel>()
                    sharedRestaurantViewModel.restaurant.value?.let {restaurant->
                        DetailScreenRoot(viewModel,restaurant, onSuccess = {
                            navController.navigate(Route.RestaurantFoodDetailScreen){
                            }
                        })
                    }
                }
                composable<Route.RestaurantFoodDetailScreen>{
                    val sharedRestaurantViewModel =
                        it.sharedKoinViewModel<RestaurantViewModel>(navController)
                    val foodDetailViewModel = koinViewModel<FoodDetailScreenViewModel>()
                    FoodDetailScreenRoot(foodDetailViewModel)
                }
                composable<Route.RestaurantHomeScreen> {
                    val sharedRestaurantViewModel =
                        it.sharedKoinViewModel<RestaurantViewModel>(navController)

                    val restaurant by sharedRestaurantViewModel.restaurant.collectAsState()

                    LaunchedEffect(Unit) {
                        sharedRestaurantViewModel.getRestaurant(onFailure = {
                            navController.navigate(Route.RestaurantDetailsScreen) {
                                navController.popBackStack(Route.RestaurantDetailsScreen, false)
                            }
                        })
                    }

                    // show the restaurant safely
                    if (restaurant != null) {
                        Text(
                            text = restaurant.toString(),
                            color = Red
                        )
                    } else {
                        // optional: loading / placeholder UI
                        Text(text = "Loading...", color = Red)
                    }
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