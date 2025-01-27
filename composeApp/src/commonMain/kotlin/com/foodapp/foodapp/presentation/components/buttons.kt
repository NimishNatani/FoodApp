package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey

@Composable
fun CustomButton(
    text: String,
    buttonColor: Color = DarkGrey,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.wrapContentWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        CustomButtonText(text)
    }
}

@Composable
fun OutlinedCustomButton(
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    textColor: Color = Black,
    borderColor: Color = Black,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.wrapContentWidth().wrapContentHeight()
            .padding( horizontal = 5.dp).border(
                BorderStroke(1.dp, borderColor),
                shape
            ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(10.dp)
    )
    {
        CustomButtonText(text = text, color = textColor)
    }
}
