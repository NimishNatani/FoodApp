package com.foodapp.foodapp

import android.app.Application
import com.foodapp.foodapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class FoodApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FoodApplication)
        }
    }
}