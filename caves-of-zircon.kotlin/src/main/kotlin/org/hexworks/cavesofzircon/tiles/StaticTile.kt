package org.hexworks.cavesofzircon.tiles

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor.YELLOW
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols

object GameTiles {

    val floor = GameTile(Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(YELLOW)
            .buildCharacterTile())

    val wall = GameTile(Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(TileColor.fromString("#999999"))
            .buildCharacterTile())
}
