package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.Red
import com.foodapp.core.presentation.White

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: Color = Red
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text = text, fontSize = 16.sp, color = White)
    }
}

@Composable
fun OutlinedCustomButton(
    text:String,
    onClick: () -> Unit,
){
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = ButtonDefaults.outlinedButtonColors(),
        shape = RoundedCornerShape(20.dp)
    )
    {        Text(text = text, fontSize = 16.sp, color = Black)
    }
}
