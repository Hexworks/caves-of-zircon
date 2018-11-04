package org.hexworks.cavesofzircon.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType

class LoseView(private val tileGrid: TileGrid) : View {

    private val screen = Screens.createScreenFor(tileGrid)
    private val colorTheme = ColorThemes.techLight()

    init {
        screen.addComponent(Components.label()
                .withText("You lost. Press [enter] to restart."))
    }

    override fun display() {
        screen.applyColorTheme(colorTheme)
        screen.display()
    }

    override fun respondToUserInput(input: Input): View {
        return if (input.inputTypeIs(InputType.Enter)) PlayView(tileGrid) else this
    }
}
