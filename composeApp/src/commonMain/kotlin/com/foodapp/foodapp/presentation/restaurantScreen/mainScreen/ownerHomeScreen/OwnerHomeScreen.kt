package com.foodapp.foodapp.presentation.restaurantScreen.mainScreen.ownerHomeScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Booking
import com.foodapp.foodapp.presentation.components.OrderCard
import kotlinx.coroutines.launch
import kotlin.collections.chunked
import kotlin.collections.forEach

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OwnerHomeScreenRoot(
    viewModel: OwnerHomeScreenViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedVisibilityScope

){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    OwnerHomeScreen(state, action =viewModel ,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope)
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OwnerHomeScreen(state: OwnerHomeScreenState,
                    action :OwnerHomeScreenAction,
sharedTransitionScope: SharedTransitionScope,
animatedContentScope: AnimatedVisibilityScope){

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val headings = listOf(Pair(BookingStatus.PENDING, 0), Pair(BookingStatus.ACCEPTED, 1))
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    var changePageState = remember {
        mutableStateOf(
            Pair(false, 0)
        )
    }
//    val columns =
//        if (screenSize.first > 1500) 3 else if (screenSize.first > 1100) 2 else if (screenSize.first > 700) 1 else 0

    val bookingStatus = groupBookings(state.bookings)

    LaunchedEffect(changePageState.value.first) {
        if (changePageState.value.first) {
            pagerState.animateScrollToPage(changePageState.value.second)
            changePageState.value = Pair(false, pagerState.currentPage)
        }
    }

    if (state.selectedBookingId != null) {
        ModalBottomSheet(
            onDismissRequest = { action.closeDetails() },
            sheetState = sheetState
        ) {
            val booking = state.bookings.firstOrNull { it.bookingId == state.selectedBookingId }
            if (booking != null) {
                OrderDetailsSheet(
                    booking,
                    onClose = { action.closeDetails() },
                    onChangeStatus = { newStatus ->
                        action.changeOrderStatus(booking.bookingId, newStatus)
                    },
                    onChat = { action.openChat(booking.bookingId) }
                )
            } else {
                Box(Modifier.height(140.dp), contentAlignment = Alignment.Center) {
                    Text("Loading...")
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(LightGrey)
    ) {
        // Header
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .background(White)
//                .padding(16.dp)
//        ) {
//            Text("Owner Home — MyRestaurant", style = MaterialTheme.typography.titleLarge)
//            Spacer(Modifier.height(4.dp))
//            Text("Manage orders, chat with customers, update statuses", color = DarkGrey)
//        }
        TopAppBar(
            title = {
                Text(
                    text = "Owner Home — MyRestaurant\nManage orders, chat with customers, update statuses",
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

//        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (heading in headings) {
                Text(
                    text = heading.first.toString(),
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
                        else -> bookingStatus["Not Accepted"] ?: emptyList()
                    }
//                    if (columns > 0) {
//                        items(bookingList.chunked(columns).reversed()) { rowItems ->
//                            Row(
//                                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                rowItems.forEach { booking ->
//                                    OrderCard(booking){
//                                        action.selectOrder(booking.bookingId)
//                                    }
//                                }
//                            }
//                        }
//                    } else {
                        items(bookingList.reversed()) { booking ->
                            OrderCard(booking){
                                        action.selectOrder(booking.bookingId)
                            }
                        }
//                    }
                }
            }
        }

        // Top (primary) tabs (scrollable)
//        val statuses = listOf(BookingStatus.PENDING, BookingStatus.ACCEPTED, BookingStatus.COMPLETED)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 12.dp)
//                .horizontalScroll(rememberScrollState()),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            statuses.forEach { status ->
//                val background = when (status) {
//                    BookingStatus.PENDING -> SandYellow
//                    BookingStatus.ACCEPTED -> Green
//                    BookingStatus.COMPLETED -> GreenShade
//                }
//                Card(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .clickable { action.selectTopStatus(status) },
//                    colors = CardDefaults.cardColors(containerColor = background),
//                    shape = MaterialTheme.shapes.medium,
//                    elevation = CardDefaults.cardElevation(2.dp)
//                ) {
//                    Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)) {
//                        Text(
//                            text = status.name.lowercase().replaceFirstChar { it.uppercase() },
//                            color = Black,
//                            style = MaterialTheme.typography.titleSmall
//                        )
//                    }
//                }
//            }
//        }
//
//        Spacer(Modifier.height(12.dp))
//
//        // Filtered list
//        val filtered = state.bookings.filter { it.status == state.selectedTopStatus }
//        if (state.isLoading) {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
//                CircularProgressIndicator(modifier = Modifier.padding(24.dp))
//            }
//        } else if (filtered.isEmpty()) {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
//                Text("No ${state.selectedTopStatus.name.lowercase()} orders", color = DarkGrey)
//            }
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(12.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(filtered) { booking ->
//                    OrderCard(booking) {
//                        action.selectOrder(booking.bookingId)
//                    }
//                }
//            }
//        }
    }
}

@Composable
private fun OrderCard(item: BookingUi, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(3.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(80.dp).background(LightGrey))
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Order #${item.bookingId}", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                Text(item.itemsSummary, color = DarkGrey)
                Spacer(Modifier.height(8.dp))
                Text("Customer: ${item.customerName} • ₹ ${item.amount ?: 0}", color = GreenShade)
            }
            Spacer(Modifier.width(8.dp))
            Text(item.status.name, modifier = Modifier.padding(8.dp))
        }
    }
}
private fun groupBookings(bookings: List<BookingUi>): Map<String, List<BookingUi>> {
    val (completedBookings, remainingBookings) = bookings.partition { it.status == BookingStatus.COMPLETED }
    val (acceptedBookings, notAcceptedBookings) = remainingBookings.partition { it.status == BookingStatus.ACCEPTED }
    return mapOf(
        "Completed" to completedBookings,
        "Accepted" to acceptedBookings,
        "Not Accepted" to notAcceptedBookings
    )
}

@Composable
private fun OrderDetailsSheet(
    booking: BookingUi,
    onClose: () -> Unit,
    onChangeStatus: (BookingStatus) -> Unit,
    onChat: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Order #${booking.bookingId}", style = MaterialTheme.typography.titleLarge)
            Text("₹ ${booking.amount ?: 0}", style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(8.dp))
        Text("Customer: ${booking.customerName}", color = DarkGrey)
        Spacer(Modifier.height(12.dp))
        Text("Items:", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(6.dp))
        Text(booking.itemsSummary, color = DarkGrey)
        Spacer(Modifier.height(18.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { onChangeStatus(BookingStatus.ACCEPTED) },
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text("Mark Accepted", color = White)
            }
            Button(
                onClick = { onChangeStatus(BookingStatus.COMPLETED) },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Mark Completed", color = White)
            }
            OutlinedButton(onClick = onChat) { Text("Chat") }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onClose, modifier = Modifier.fillMaxWidth()) {
            Text("Close")
        }
    }
}