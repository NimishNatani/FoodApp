package com.foodapp.foodapp.screensize

import com.foodapp.foodapp.presentation.components.PlatformConfiguration
import java.awt.Toolkit

class DesktopScreenSize:PlatformConfiguration {
    override fun screenWidth(): Int {
        return Toolkit.getDefaultToolkit().screenSize.width
    }

    override fun screenHeight(): Int {
        return Toolkit.getDefaultToolkit().screenSize.height
    }

}