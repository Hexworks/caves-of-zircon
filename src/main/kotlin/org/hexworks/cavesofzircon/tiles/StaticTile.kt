package org.hexworks.cavesofzircon.tiles

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols

enum class StaticTile(val tile: GameTile) {

    FLOOR(GameTile(Tiles.newBuilder()
            .character(Symbols.INTERPUNCT)
            .foregroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile())),
    WALL(GameTile(Tiles.newBuilder()
            .character('#')
            .foregroundColor(TileColor.fromString("#999999"))
            .buildCharacterTile()))
}
