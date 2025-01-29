package com.foodapp.foodapp.screensize

import android.content.Context
import com.foodapp.foodapp.presentation.components.PlatformConfiguration

class ScreenSize(private val context: Context):PlatformConfiguration {
    override fun screenWidth(): Float {
        val density = context.resources.displayMetrics.density
        return context.resources.displayMetrics.widthPixels/density
    }

    override fun screenHeight(): Float {
        val density = context.resources.displayMetrics.density
        return context.resources.displayMetrics.heightPixels/density
    }
}