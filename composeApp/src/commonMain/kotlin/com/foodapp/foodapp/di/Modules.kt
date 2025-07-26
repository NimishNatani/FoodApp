package com.foodapp.foodapp.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.foodapp.core.di.HttpClientFactory
import com.foodapp.foodapp.data.api.AuthApi
import com.foodapp.foodapp.data.api.BookingApi
import com.foodapp.foodapp.data.api.RestaurantApi
import com.foodapp.foodapp.data.api.UserApi
import com.foodapp.foodapp.data.repository.AuthRepositoryImpl
import com.foodapp.foodapp.data.repository.UserRepositoryImpl
import com.foodapp.foodapp.data.repository.RestaurantRepositoryImpl
import com.foodapp.foodapp.domain.repository.AuthRepository
import com.foodapp.foodapp.domain.repository.BookingRepository
import com.foodapp.foodapp.domain.repository.RestaurantRepository
import com.foodapp.foodapp.domain.repository.UserRepository
import com.foodapp.foodapp.presentation.RestaurantViewModel
import com.foodapp.foodapp.presentation.UserViewModel
import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import com.foodapp.foodapp.presentation.login.AuthLoginViewModel
import com.foodapp.foodapp.presentation.register.AuthRegisterViewModel
import com.foodapp.foodapp.presentation.starter.AuthValidationViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.UserMainScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.cartScreen.CartScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.foodScreen.ViewFoodScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.homeScreen.UserHomeScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.restaurantScreen.ViewRestaurantScreenViewModel
import com.foodapp.foodapp.storage.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.foodapp.foodapp.data.repository.BookingRepositoryImpl
import com.foodapp.foodapp.presentation.restaurantScreen.detailScreen.DetailScreenViewModel
import com.foodapp.foodapp.presentation.userScreen.mainScreen.screens.bookingScreen.BookingScreenViewModel


expect val platformModule: Module
expect fun createTokenStorageModule(): Module
expect fun getPlatformConfiguration(): Module
expect fun getLocationModule():Module



val appModule = module {
//    single { CIO.create() } // Use CIO engine or any other engine you added

    single<HttpClientEngine> { CIO.create() } // Provide the CIO engine


//    single { HttpClientFactory.create(get()) }
    single { HttpClientFactory(get()).create(get()) } // Pass the HttpClientEngine


//    single<TokenStorage> { createTokenStorageModule() }


    single { AuthApi(get(),get()) }
    single { UserApi(get(),get()) }
    single { RestaurantApi(get(),get()) }
    single { BookingApi(get(),get()) }

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::RestaurantRepositoryImpl).bind<RestaurantRepository>()
    singleOf(::BookingRepositoryImpl).bind<BookingRepository>()

    viewModel { AuthRegisterViewModel(authRepository = get(), get()) }
    viewModel { AuthLoginViewModel(authRepository = get(), get()) }
    viewModel { AuthValidationViewModel(authRepository = get()) }
    viewModel { UserMainScreenViewModel( ) }
    viewModel { UserHomeScreenViewModel(get(),get(),get(),get())}
    viewModel { ViewRestaurantScreenViewModel(get())}
    viewModel { ViewFoodScreenViewModel(get()) }
    viewModel { CartScreenViewModel(get(),get(),get()) }
    viewModel { BookingScreenViewModel(get(),get())}
    viewModel { UserViewModel(  get()) }
    viewModel { RestaurantViewModel(  get()) }
    viewModel { DetailScreenViewModel(get(),get()) }

}

@Composable
expect fun PickImage(onImageSelected: (Pair<ImageBitmap, ByteArray>) -> Unit)