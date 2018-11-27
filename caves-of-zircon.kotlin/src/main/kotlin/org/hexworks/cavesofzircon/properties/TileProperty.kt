package org.hexworks.cavesofzircon.properties

import org.hexworks.cavesofzircon.blocks.GameTile

data class TileProperty(val tile: GameTile) : Property {
    override val name: String
        get() = "Tile(${tile.character})"
}
