package com.foodapp.foodapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.foodapp.App
import com.foodapp.foodapp.storage.TokenStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
            // AndroidMain source set

        }
    }
}



@Preview
@Composable
fun AppAndroidPreview() {
    App()
}