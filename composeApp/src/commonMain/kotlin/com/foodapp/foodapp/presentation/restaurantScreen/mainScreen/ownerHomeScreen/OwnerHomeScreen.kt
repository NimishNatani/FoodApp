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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.Green
import com.foodapp.core.presentation.GreenShade
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.SandYellow
import com.foodapp.core.presentation.White
import com.foodapp.foodapp.domain.models.Booking
import kotlinx.coroutines.launch

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
        Column(
            Modifier
                .fillMaxWidth()
                .background(White)
                .padding(16.dp)
        ) {
            Text("Owner Home — MyRestaurant", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text("Manage orders, chat with customers, update statuses", color = DarkGrey)
        }

        Spacer(Modifier.height(12.dp))

        // Top (primary) tabs (scrollable)
        val statuses = listOf(BookingStatus.PENDING, BookingStatus.ACCEPTED, BookingStatus.COMPLETED)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            statuses.forEach { status ->
                val background = when (status) {
                    BookingStatus.PENDING -> SandYellow
                    BookingStatus.ACCEPTED -> Green
                    BookingStatus.COMPLETED -> GreenShade
                }
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable { action.selectTopStatus(status) },
                    colors = CardDefaults.cardColors(containerColor = background),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)) {
                        Text(
                            text = status.name.lowercase().replaceFirstChar { it.uppercase() },
                            color = Black,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Filtered list
        val filtered = state.bookings.filter { it.status == state.selectedTopStatus }
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                CircularProgressIndicator(modifier = Modifier.padding(24.dp))
            }
        } else if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                Text("No ${state.selectedTopStatus.name.lowercase()} orders", color = DarkGrey)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered) { booking ->
                    OrderCard(booking) {
                        action.selectOrder(booking.bookingId)
                    }
                }
            }
        }
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