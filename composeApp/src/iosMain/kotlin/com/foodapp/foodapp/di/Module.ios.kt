package com.foodapp.foodapp.di

import com.foodapp.foodapp.storage.IOSTokenStorage
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
    }

actual fun createTokenStorageModule(): Module = module {
    single<TokenStorage> { IOSTokenStorage() }
}