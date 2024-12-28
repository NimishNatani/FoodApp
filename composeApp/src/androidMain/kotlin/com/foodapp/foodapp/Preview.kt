package com.foodapp.foodapp

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.foodapp.foodapp.presentation.components.CustomTextField
import com.foodapp.foodapp.presentation.components.FoodCard
import com.foodapp.foodapp.presentation.components.RestaurantCard
import com.foodapp.foodapp.presentation.login.LoginIntent
import com.foodapp.foodapp.presentation.login.LoginScreen
import com.foodapp.foodapp.presentation.login.LoginUiState
import com.foodapp.foodapp.presentation.navigation.Route
import com.foodapp.foodapp.presentation.register.RegisterScreen
import com.foodapp.foodapp.presentation.register.RegisterUiState
import com.foodapp.foodapp.presentation.userScreen.mainScreen.MainScreenState
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreen
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreen
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenState

//@Preview
//@Composable
//fun CustomTextFieldPreview() {
//    CustomTextField(
//        label = "Enter your name",
//        value = "John Doe",
//        onValueChange = {},
//        type = "password"
//
//    )
//}
//
//@Preview(backgroundColor = Color.WHITE.toLong(), showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen(
//        state = RegisterUiState(),
//        onEvent = {},
//        onLogin = {},
//        isUser = true
//    )
//}

//@Preview(backgroundColor = Color.WHITE.toLong(), showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(
//        state = LoginUiState(),
//        onEvent = {},
//        isUser = true,
//        onSignUp = {  },
//        modifier = Modifier
//    )
//}

//


@Preview(backgroundColor = Color.WHITE.toLong(), showBackground = true)
@Composable
fun LoginScreenPreview() {
    RestaurantCard(
        imageUrl = "https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg",
        name = "Restaurant Name",
        tags = listOf("Tag1", "Tag2", "Tag3"),
        rating = "4.5",
        totalReviews = 11,
        distance = "1.5",
        isFavorite = false,
        onFavoriteClick = {},
        onClick = {},
        address = "jaiput fhskdndjdvk"
    )
}

//@Preview(backgroundColor = Color.WHITE.toLong(), showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    FoodCard(
//        imageUrl = "https://www.foodiesfeed.com/wp-content/uploads/2023/06/burger-with-melted-cheese.jpg",
//        name = "Restaurant Name",
//        rating = "4.5",
//        distance = "1.5",
//        isFavorite = false,
//        onFavoriteClick = {},
//        price = "100"
//    )
//}

//@Preview(backgroundColor = Color.WHITE.toLong(), showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//   UserHomeScreen(
//       state = UserHomeScreenState(isLoading = false),
//       onAction = {},
//       onViewAllRestaurantScreen = {}
//
//   )
//}