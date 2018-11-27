package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.Game
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onInputOfType
import org.hexworks.zircon.api.kotlin.onKeyStroke


class PlayView(tileGrid: TileGrid) : BaseView(tileGrid) {

    private val game = Game(screen)

    init {
        screen.onInputOfType(InputType.Enter) {
            WinView(tileGrid).dock()
        }
        screen.onInputOfType(InputType.Escape) {
            LoseView(tileGrid).dock()
        }
        screen.onKeyStroke {
            game.addInput(it)
        }
    }
}
