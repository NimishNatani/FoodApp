package com.foodapp.foodapp.di

import com.foodapp.foodapp.storage.DesktopTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
//        single { providePlatformTokenStorage(get()) } // Inject Android Context
    }

actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { DesktopTokenStorage() }
}