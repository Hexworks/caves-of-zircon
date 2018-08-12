package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType.Enter
import org.hexworks.zircon.api.input.InputType.Escape

class PlayView(private val tileGrid: TileGrid) : View {

    private val screen = Screens.createScreenFor(tileGrid)
    private val world = WorldBuilder(tileGrid).build()

    init {
        world.tiles.forEach { pos, tile ->
            screen.setTileAt(pos, tile)
        }
    }

    override fun display() {
        screen.display()
    }

    override fun respondToUserInput(input: Input): View {
        return when (input.getInputType()) {
            Enter -> WinView(tileGrid)
            Escape -> LoseView(tileGrid)
            else -> this
        }
    }
}
