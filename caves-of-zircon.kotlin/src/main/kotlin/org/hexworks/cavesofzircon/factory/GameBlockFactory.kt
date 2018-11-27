package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameBlockType
import org.hexworks.cavesofzircon.repository.GameTileRepository

object GameBlockFactory {

    fun floor() = GameBlock(
            tile = GameTileRepository.floor,
            type = GameBlockType.FLOOR)

    fun wall() = GameBlock(
            tile = GameTileRepository.wall,
            type = GameBlockType.WALL)
}
