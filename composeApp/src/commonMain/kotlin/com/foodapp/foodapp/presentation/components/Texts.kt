package com.foodapp.foodapp.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.foodapp.core.presentation.TextSize
import com.foodapp.core.presentation.White

@Composable
fun CustomButtonText(text:String){
    Text(text = text, fontSize = TextSize.button, color = White, fontWeight = FontWeight.SemiBold)
}
