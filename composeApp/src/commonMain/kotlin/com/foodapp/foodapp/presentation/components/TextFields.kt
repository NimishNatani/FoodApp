package com.foodapp.foodapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodapp.core.presentation.Black
import com.foodapp.core.presentation.DarkGrey
import com.foodapp.core.presentation.LightGrey
import com.foodapp.core.presentation.White
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_invisible
import kotlinproject.composeapp.generated.resources.ic_visible
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    type: String,
    backgroundColor: Color = White,
    textColor:Color = Black,
    borderColor:Color = DarkGrey
) {
    // State to manage password visibility
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 16.sp, color = DarkGrey) },
        visualTransformation = if (type == "password" && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = when (type) {
                "password" -> KeyboardType.Password
                "email" -> KeyboardType.Email
                "phone" -> KeyboardType.Phone
                "number" -> KeyboardType.Number
                else -> KeyboardType.Text
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(White),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = textColor,
            focusedTextColor = textColor,
            unfocusedBorderColor = borderColor,
            focusedBorderColor = borderColor,
            unfocusedContainerColor = backgroundColor,
            focusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,

        ),
        trailingIcon = {
            if (type == "password") {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = if (passwordVisible) painterResource(Res.drawable.ic_visible) else painterResource(Res.drawable.ic_invisible),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )

                }
            }
        }
    )
}