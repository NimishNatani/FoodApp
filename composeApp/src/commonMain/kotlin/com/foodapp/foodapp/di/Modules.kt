package com.foodapp.foodapp.di

import com.foodapp.core.di.HttpClientFactory
import com.foodapp.foodapp.data.api.AuthApi
import com.foodapp.foodapp.data.repository.AuthRepositoryImpl
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.presentation.login.AuthLoginViewModel
import com.foodapp.foodapp.presentation.register.AuthRegisterViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    single { CIO.create() } // Use CIO engine or any other engine you added

    single { HttpClientFactory.create(get()) }

    single { AuthApi(get()) }

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    viewModelOf (:: AuthRegisterViewModel)
    viewModelOf ( ::AuthLoginViewModel )
}