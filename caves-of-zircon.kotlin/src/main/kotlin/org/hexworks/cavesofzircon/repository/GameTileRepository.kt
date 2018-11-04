package org.hexworks.cavesofzircon.repository

import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {

    val floor = GameTile(Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(ANSITileColor.YELLOW)
            .buildCharacterTile())

    val wall = GameTile(Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(TileColor.fromString("#999999"))
            .buildCharacterTile())

    val player = GameTile(Tiles.newBuilder()
            .withCharacter('@')
            .withForegroundColor(TileColor.fromString("#ffffff"))
            .buildCharacterTile())

    val empty = GameTile(Tiles.empty())
}
