package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.presentation.components.FoodItemCard
import com.foodapp.foodapp.presentation.components.OrderCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserBookingScreenRoot(
    viewModel: BookingScreenViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val screenSize = viewModel.getScreenSize()

    UserBookingScreen(
        state,
        onAction = { action ->
            viewModel.onAction(action)
        }, screenSize = screenSize
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserBookingScreen(state: BookingScreenState, onAction: (BookingScreenAction) -> Unit,    screenSize: Pair<Int, Int>
) {
    val columns =
        if (screenSize.first > 1200) 4 else if (screenSize.first > 800) 3 else if (screenSize.first > 400) 2 else 1
    val headings = listOf(Pair("InProgress",0), Pair("Completed",1), Pair("Cancelled",2))
    val pagerState = rememberPagerState(pageCount = {3}, initialPage = 0)
    TopAppBar(
        title = {
            Text(
                text = "Orders",
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = TextSize.large, modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        )
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (heading in headings) {
            Text(
                text = heading.first,
                fontSize = TextSize.large,
                fontWeight = FontWeight.SemiBold,
                color = if(pagerState.currentPage==heading.second){
                    Green} else DarkGrey
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        thickness = 0.5.dp,
        color = GreenShade
    )
    HorizontalPager(state = pagerState) { page ->
        if (state.isLoading) {
            Text("Loading")
        } else if (!state.isLoading && state.errorMessage != null) {
            Text(state.errorMessage)
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.orderList.chunked(columns)) { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { booking ->
                            OrderCard(
                                booking, page
                            )
                        }
                    }
                }
            }
        }
    }
}