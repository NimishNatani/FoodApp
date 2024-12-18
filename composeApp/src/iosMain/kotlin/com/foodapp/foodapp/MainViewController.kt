package com.foodapp.foodapp

import androidx.compose.ui.window.ComposeUIViewController
import com.foodapp.App
import com.foodapp.foodapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }