package com.foodapp.foodapp.di

import com.foodapp.foodapp.location.DesktopLocationClass
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import com.foodapp.foodapp.presentation.location.LocationInterface
import com.foodapp.foodapp.screensize.DesktopScreenSize
import com.foodapp.foodapp.storage.DesktopTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.awt.Toolkit

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
//        single { providePlatformTokenStorage(get()) } // Inject Android Context
    }

actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { DesktopTokenStorage() }
}


actual fun getPlatformConfiguration(): Module =module {
        single<PlatformConfiguration> {DesktopScreenSize()}
    }
actual fun getLocationModule(): Module =module {
        single<LocationInterface> {DesktopLocationClass()}
    }
