package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.tiles.GameBlock
import org.hexworks.cavesofzircon.tiles.GameTile
import org.hexworks.cavesofzircon.tiles.GameTiles
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.game.BaseGameArea
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.util.TreeMap
import org.hexworks.zircon.platform.factory.TreeMapFactory

class World(tiles: Map<Position, GameTile>,
            initialSize: Size) : BaseGameArea<GameTile, GameBlock>() {

    private val size3D = Sizes.from2DTo3D(initialSize, 1)
    private val blocks: TreeMap<Position3D, GameBlock> = TreeMapFactory.create()
    private val filler = GameBlock()

    override val defaultBlock = GameBlock()
    override val size = size3D

    init {
        tiles.forEach { pos, tile ->
            val pos3D = Positions.from2DTo3D(pos)
            setBlockAt(pos3D, GameBlock(
                    position = pos3D,
                    layers = mutableListOf(tile),
                    emptyTile = GameTiles.floor))
        }
    }

    override fun layersPerBlock() = 1

    override fun hasBlockAt(position: Position3D) = blocks.containsKey(position)

    override fun fetchBlockAt(position: Position3D): Maybe<GameBlock> {
        return Maybe.ofNullable(blocks[position])
    }

    override fun fetchBlockOrDefault(position: Position3D) =
            blocks.getOrDefault(position, filler.withPosition(position))

    override fun fetchBlocks(): Iterable<GameBlock> {
        return blocks.values.toList()
    }

    override fun setBlockAt(position: Position3D, block: GameBlock) {
        require(size.containsPosition(position)) {
            "The supplied position ($position) is not within the size ($size3D) of this game area."
        }
        val layerCount = block.layers.size
        require(layerCount == layersPerBlock()) {
            "The number of layers per block for this game area is ${layersPerBlock()}." +
                    " The supplied layers have a size of $layerCount."
        }
        blocks[position] = block
    }
}
