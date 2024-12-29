package com.foodapp.foodapp.screensize

import android.content.Context
import com.foodapp.foodapp.presentation.components.PlatformConfiguration

class ScreenSize(private val context: Context):PlatformConfiguration {
    override fun screenWidth(): Int {
        return context.resources.displayMetrics.widthPixels
    }

    override fun screenHeight(): Int {
        return context.resources.displayMetrics.heightPixels
    }
}