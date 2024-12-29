package com.foodapp.foodapp.screensize

import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

class IOSScreenSize:PlatformConfiguration {
    @OptIn(ExperimentalForeignApi::class)
    override fun screenWidth(): Int {
        return CGRectGetWidth(UIScreen.mainScreen.bounds).toInt()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun screenHeight(): Int {
        return CGRectGetHeight(UIScreen.mainScreen.bounds).toInt()
    }
}