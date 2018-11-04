package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameBlockType
import org.hexworks.cavesofzircon.repository.GameTileRepository

object GameBlockFactory {

    fun floor() = GameBlock(GameTileRepository.floor, GameBlockType.FLOOR)

    fun wall() = GameBlock(GameTileRepository.wall, GameBlockType.WALL)
}
