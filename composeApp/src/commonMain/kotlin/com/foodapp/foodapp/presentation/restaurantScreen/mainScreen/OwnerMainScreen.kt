package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.White
import com.foodapp.core.presentation.TextSize
import com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen.OwnerHomeScreenRoot
import com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen.OwnerHomeScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.BottomNavItem
import compose.icons.Octicons
import compose.icons.octicons.History16
import compose.icons.octicons.Home16
import compose.icons.octicons.Home24
import compose.icons.octicons.PaperAirplane16
import compose.icons.octicons.Person16
import compose.icons.octicons.Project16
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OwnerMainScreenRoot(viewModel: OwnerMainScreenViewModel = koinViewModel(),
                        sharedTransitionScope: SharedTransitionScope,
                        animatedContentScope: AnimatedVisibilityScope,){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    OwnerMainScreen(state,onAction = { action -> viewModel.onAction(action)}, sharedTransitionScope, animatedContentScope)

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OwnerMainScreen(
    state: OwnerMainScreenState,
    onAction: (OwnerMainScreenAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope
) {
    val ownerHomeScreenViewModel = koinViewModel<OwnerHomeScreenViewModel>()

    val items = listOf(
        BottomNavItem(0, Octicons.Home16, "Home"),
        BottomNavItem(1, Octicons.PaperAirplane16, "Chats"),
        BottomNavItem(2, Octicons.Project16, "Analytics"),
        BottomNavItem(3, Octicons.History16, "History"),
        BottomNavItem(4, Octicons.Person16, "Profile")
    )

    LaunchedEffect(Unit){

    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(
                targetState = state.selectedTabIndex,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = AnimatedContentTransitionScope.SlideDirection.Up
                    ).togetherWith(
                        slideOutOfContainer(
                            animationSpec = tween(300),
                            towards = AnimatedContentTransitionScope.SlideDirection.Down
                        )
                    )
                }
            ) { target ->
                when (target) {
                    0 -> OwnerHomeScreenRoot(ownerHomeScreenViewModel,sharedTransitionScope, animatedContentScope)
//                    1 -> OwnerChatScreenRoot()
//                    2 -> OwnerAnalyticsScreenRoot()
//                    3 -> OwnerHistoryScreenRoot()
//                    4 -> OwnerProfileScreenRoot()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, shape = RoundedCornerShape(topStart = 20.dp))
                .border(1.dp, Green, RoundedCornerShape(topStart = 20.dp))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomNavItemView(
                    item = item,
                    isSelected = item.iconNumber == state.selectedTabIndex,
                    onClick = { onAction(OwnerMainScreenAction.OnTabSelected(item.iconNumber)) }
                )
            }
        }
    }
}

@Composable
fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
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
        Text(
            text = item.name,
            fontSize = TextSize.regular,
            color = if (isSelected) Green else DarkGrey
        )
    }
}
