package com.foodapp.foodapp.di

import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import com.foodapp.foodapp.screensize.IOSScreenSize
import com.foodapp.foodapp.storage.IOSTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
    }

actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { IOSTokenStorage() }
}

@OptIn(ExperimentalForeignApi::class)
actual fun getPlatformConfiguration(): Module = module {
        single<PlatformConfiguration> {IOSScreenSize()}
    }
