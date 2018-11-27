package org.hexworks.cavesofzircon.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.grid.TileGrid

abstract class BaseView(tileGrid: TileGrid) : View {

    final override val screen = Screens.createScreenFor(tileGrid)
    private val colorTheme = ColorThemes.arc()

    final override fun dock() {
        screen.display()
        screen.applyColorTheme(colorTheme)
    }
}
