package com.foodapp.foodapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.foodapp.App
import com.foodapp.foodapp.di.initKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        App()
    }
}