package org.hexworks.cavesofzircon.builders

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
            .withForegroundColor(ANSITileColor.GRAY)
            .buildCharacterTile())

    val player = GameTile(Tiles.newBuilder()
            .withCharacter('@')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .buildCharacterTile())

    val fungus = GameTile(Tiles.newBuilder()
            .withCharacter('f')
            .withForegroundColor(ANSITileColor.GREEN)
            .buildCharacterTile())

    val stairsUp = GameTile(Tiles.newBuilder()
            .withCharacter('<')
            .withForegroundColor(ANSITileColor.WHITE)
            .buildCharacterTile())

    val stairsDown = GameTile(Tiles.newBuilder()
            .withCharacter('>')
            .withForegroundColor(ANSITileColor.WHITE)
            .buildCharacterTile())



    val empty = GameTile(Tiles.empty())
}
