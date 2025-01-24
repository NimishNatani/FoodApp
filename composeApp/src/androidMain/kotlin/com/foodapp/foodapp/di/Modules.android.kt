package com.foodapp.foodapp.di

import com.foodapp.foodapp.location.AndroidLocationInterface
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import com.foodapp.foodapp.presentation.location.LocationInterface
import com.foodapp.foodapp.screensize.ScreenSize
import com.foodapp.foodapp.storage.AndroidTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }
actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { AndroidTokenStorage(androidContext()) }
}
actual fun getPlatformConfiguration(): Module = module {
        single<PlatformConfiguration> { ScreenSize(androidContext()) }
    }
actual fun getLocationModule():Module = module {
    single<LocationInterface> { AndroidLocationInterface() }
}
