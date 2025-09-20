package com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.presentation.components.OrderCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserBookingScreenRoot(
    viewModel: BookingScreenViewModel = koinViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (state.apiCalling < 1) {
            viewModel.onAction(BookingScreenAction.GetOrderList)
        }

    }
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
fun UserBookingScreen(
    state: BookingScreenState, onAction: (BookingScreenAction) -> Unit, screenSize: Pair<Float, Float>
) {
    println("Screen Width: ${screenSize.first}")
    val columns =
        if (screenSize.first > 1500) 3 else if (screenSize.first > 1100) 2 else if (screenSize.first > 700) 1 else 0
    val headings = listOf(Pair("InProgress", 0), Pair("Completed", 1), Pair("Cancelled", 2))
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0)
    var changePageState = remember {
        mutableStateOf(
            Pair(false, 0)
        )
    }
    val bookingStatus = groupBookings(state.orderList)

    LaunchedEffect(changePageState.value.first) {
        if (changePageState.value.first) {
            pagerState.animateScrollToPage(changePageState.value.second)
            changePageState.value = Pair(false, pagerState.currentPage)
        }
    }
    Column(modifier = Modifier.fillMaxWidth().background(LightGrey)) {
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
                containerColor = LightGrey
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (heading in headings) {
                Text(
                    text = heading.first,
                    fontSize = TextSize.large,
                    fontWeight = FontWeight.SemiBold,
                    color = if (pagerState.currentPage == heading.second) {
                        Green
                    } else DarkGrey,
                    modifier = Modifier.clickable {
                        changePageState.value = Pair(true, heading.second)
                    }
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            thickness = 0.5.dp,
            color = GreenShade
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) { page ->
            if (state.isLoading) {
                Text("Loading")
            } else if (!state.isLoading && state.errorMessage != null) {
                Text(state.errorMessage)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val bookingList = when (page) {
                        0 -> bookingStatus["Accepted"] ?: emptyList()
                        1 -> bookingStatus["Completed"] ?: emptyList()
                        else -> bookingStatus["Not Accepted"] ?: emptyList()
                    }
                    if (columns > 0) {
                        items(bookingList.chunked(columns).reversed()) { rowItems ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowItems.forEach { booking ->
                                    OrderCard(
                                        booking = booking,
                                        page = page,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        items(bookingList.reversed()) { booking ->
                            OrderCard(
                                booking = booking,
                                page = page,
                                modifier = Modifier.fillMaxWidth().padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun groupBookings(bookings: List<Booking>): Map<String, List<Booking>> {
    val (completedBookings, remainingBookings) = bookings.partition { it.isBookingCompleted }
    val (acceptedBookings, notAcceptedBookings) = remainingBookings.partition { it.isAcceptedByRestaurant }

    return mapOf(
        "Completed" to completedBookings,
        "Accepted" to acceptedBookings,
        "Not Accepted" to notAcceptedBookings
    )
}