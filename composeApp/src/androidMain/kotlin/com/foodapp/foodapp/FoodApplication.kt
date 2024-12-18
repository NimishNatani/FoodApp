package com.foodapp.foodapp

import android.app.Application
import com.foodapp.foodapp.di.appModule
import com.foodapp.foodapp.di.initKoin
import com.foodapp.foodapp.di.platformModule
import org.koin.android.ext.koin.androidContext

class FoodApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FoodApplication)
            modules(listOf(appModule, platformModule))  // Include both shared and platform-specific modules

        }
    }
}