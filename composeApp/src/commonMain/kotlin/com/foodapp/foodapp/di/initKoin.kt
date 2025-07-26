package com.foodapp.foodapp.di

import androidx.compose.ui.graphics.ImageBitmap
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, platformModule, createTokenStorageModule(), getPlatformConfiguration(),
            getLocationModule()
        ) // Only include ViewModels module
    }
}