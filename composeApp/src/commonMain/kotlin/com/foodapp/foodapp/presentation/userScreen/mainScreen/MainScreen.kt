package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Restaurant
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen.UserBookingScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.favoriteScreen.UserFavoriteScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenAction
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.profileScreen.UserProfileScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserMainScreenRoot(
    viewModel: UserMainScreenViewModel = koinViewModel(),
    onViewAllRestaurantScreen: (List<Restaurant>) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    UserMainScreen(state, onAction = { action ->
        viewModel.onAction(action)
    },
        onViewAllRestaurantScreen = { onViewAllRestaurantScreen(it) })

}

@Composable
fun UserMainScreen(
    state: MainScreenState,
    onAction: (MainScreenAction) -> Unit,
    onViewAllRestaurantScreen: (List<Restaurant>) -> Unit
) {
    val userHomeScreenViewModel = koinViewModel<UserHomeScreenViewModel>()
    val items = listOf(
        BottomNavItem(0, Icons.Default.Home,"Home"),
        BottomNavItem(1, Icons.Default.ShoppingCart,"My Cart"),
        BottomNavItem(2, Icons.Default.AccountBox,"Orders"),
        BottomNavItem(3, Icons.Default.Person,"Me")
    )
    LaunchedEffect(Unit) {
        userHomeScreenViewModel.onAction(UserHomeScreenAction.OnGettingRestaurants("Jaipur"))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (state.selectedTabIndex.iconNumber) {
                0 -> {
                    UserHomeScreenRoot(userHomeScreenViewModel, onNotificationClick = {},
                        onViewAllRestaurantScreen = { onViewAllRestaurantScreen(it) })
                }

                1 -> {
                    UserBookingScreenRoot()
                }

                2 -> {
                    UserFavoriteScreenRoot()
                }

                3 -> {
                    UserProfileScreenRoot()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 0.dp), color = White)
                .border(width = 1.dp, color = Green, shape = RoundedCornerShape(topStart = 20.dp))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isSelected = item.iconNumber == state.selectedTabIndex.iconNumber,
                    onClick = {
                        onAction(
                            MainScreenAction.OnTabSelected(
                                item.iconNumber,
                                item.iconResId,
                                item.name
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = item.iconResId,
            contentDescription = "bottom nav",
            tint = if (isSelected) Green else DarkGrey,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = item.name, fontSize = TextSize.regular, color = if (isSelected) Green else DarkGrey)

    }
}

data class BottomNavItem(
    val iconNumber: Int,
    val iconResId: ImageVector,
    val name :String
)
