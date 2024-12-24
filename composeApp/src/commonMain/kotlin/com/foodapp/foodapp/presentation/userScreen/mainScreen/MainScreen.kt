package com.foodapp.foodapp.presentation.userScreen.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen.UserBookingScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.favoriteScreen.UserFavoriteScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenRoot
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.profileScreen.UserProfileScreenRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserMainScreenRoot(
    viewModel: UserMainScreenViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    UserMainScreen(state, onAction = { action ->
        viewModel.onAction(action)
    })

}

@Composable
fun UserMainScreen(state: MainScreenState, onAction: (MainScreenAction) -> Unit) {
val userHomeScreenViewModel = koinViewModel<UserHomeScreenViewModel>()
    val items = listOf(
        BottomNavItem(0, Icons.Default.Home),
        BottomNavItem(1, Icons.Default.ShoppingCart),
        BottomNavItem(2, Icons.Default.Favorite),
        BottomNavItem(3, Icons.Default.Person)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (state.selectedTabIndex.iconNumber) {
                0 -> {
                    UserHomeScreenRoot(userHomeScreenViewModel, onNotificationClick = {})
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
                .background(shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp), color = SandYellow)
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
                                item.iconResId
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
            tint = if (isSelected) Red else DarkGrey,
            modifier = Modifier.size(24.dp)
        )

    }
}

data class BottomNavItem(
    val iconNumber: Int,
    val iconResId: ImageVector
)
