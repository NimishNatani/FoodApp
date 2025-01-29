package com.foodapp.foodapp.screensize

import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.UIKit.UIScreen

class IOSScreenSize:PlatformConfiguration {
    @OptIn(ExperimentalForeignApi::class)
    override fun screenWidth(): Float {
        return CGRectGetWidth(UIScreen.mainScreen.bounds)
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun screenHeight(): Float {
        return CGRectGetHeight(UIScreen.mainScreen.bounds)
    }
}