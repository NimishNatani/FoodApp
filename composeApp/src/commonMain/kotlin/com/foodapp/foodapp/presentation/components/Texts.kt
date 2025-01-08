package com.foodapp.foodapp.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White

@Composable
fun CustomButtonText(text:String,color: Color = White){
    Text(text = text, fontSize = TextSize.button, color = color, fontWeight = FontWeight.SemiBold)
}
