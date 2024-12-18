package com.foodapp.foodapp.presentation.starter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.foodapp.foodapp.presentation.navigation.Route

@Composable
fun UserSelectionScreen(onUserSelected: (isUser: Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Who are you?", fontSize = 24.sp, modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = { onUserSelected( true) },
            modifier = Modifier.size(150.dp)
        ) {
            Text("User", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onUserSelected(false) },
            modifier = Modifier.size(150.dp)
        ) {
            Text("Restaurant", fontSize = 20.sp)
        }
    }
}
