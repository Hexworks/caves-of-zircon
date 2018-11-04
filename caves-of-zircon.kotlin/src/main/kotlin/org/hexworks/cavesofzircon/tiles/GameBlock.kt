package org.hexworks.cavesofzircon.tiles

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.base.BlockBase
import org.hexworks.zircon.api.data.impl.Position3D

data class GameBlock(override val position: Position3D = Positions.default3DPosition(),
                     override val layers: MutableList<GameTile> = mutableListOf(),
                     private val sides: Map<BlockSide, GameTile> = mutableMapOf(),
                     private val emptyTile: GameTile = GameTiles.floor) : BlockBase<GameTile>() {

    override fun withPosition(position: Position3D): GameBlock {
        return copy(position = position)
    }

    override fun fetchSide(side: BlockSide): GameTile {
        return sides.getOrDefault(side, emptyTile)
    }
}
