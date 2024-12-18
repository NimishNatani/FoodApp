package com.foodapp.foodapp.di

import android.content.Context
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