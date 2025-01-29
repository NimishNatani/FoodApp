package com.foodapp.foodapp.screensize

import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import java.awt.Toolkit

class DesktopScreenSize:PlatformConfiguration {
    override fun screenWidth(): Float {
        val dpi = Toolkit.getDefaultToolkit().screenResolution // Get actual DPI

        return (Toolkit.getDefaultToolkit().screenSize.width/ (dpi / 160f))
    }

    override fun screenHeight(): Float {
        val dpi = Toolkit.getDefaultToolkit().screenResolution // Get actual DPI
        return (Toolkit.getDefaultToolkit().screenSize.height/ (dpi / 160f))
    }

}