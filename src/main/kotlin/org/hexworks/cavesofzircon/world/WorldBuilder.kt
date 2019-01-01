package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.builders.GameBlockFactory
import org.hexworks.cavesofzircon.extensions.isEmptyFloor
import org.hexworks.cavesofzircon.extensions.isWall
import org.hexworks.cavesofzircon.extensions.sameLevelNeighborsShuffled
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D


class WorldBuilder(private val worldSize: Size3D) {

    private val width = worldSize.xLength
    private val height = worldSize.zLength
    private var blocks: MutableMap<Position3D, GameBlock> = mutableMapOf()
    private var nextRegion: Int = 0
    private var positionToRegionLookup: MutableMap<Position3D, Int> = mutableMapOf()

    fun makeCaves(): WorldBuilder {
        return randomizeTiles()
                .smooth(8)
                .createRegions()
                .connectRegions()
                .addExitStairs()
    }

    fun build(visibleSize: Size3D, worldSize: Size3D): World = World(blocks, visibleSize, worldSize)

    private fun randomizeTiles(): WorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = if (Math.random() < 0.5) {
                GameBlockFactory.floor()
            } else GameBlockFactory.wall(pos)
        }
        return this
    }

    private fun smooth(iterations: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    blocks.whenPresent(neighbor) { block ->
                        if (block.isEmptyFloor) {
                            floors++
                        } else rocks++
                    }
                }
                newBlocks[Positions.create3DPosition(x, y, z)] = if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall(pos)
            }
            blocks = newBlocks
        }
        return this
    }

    private fun createRegions(): WorldBuilder {
        forAllPositions { pos ->
            if (blocks[pos].isWall.not() && positionToRegionLookup.containsKey(pos).not()) {
                fillRegion(nextRegion++, pos)
            }
        }
        return this
    }

    private fun fillRegion(region: Int, pos: Position3D): Int {
        var size = 1
        val regionTileQueue = mutableListOf(pos)
        positionToRegionLookup[pos] = region
        while (regionTileQueue.isNotEmpty()) {
            val position = regionTileQueue.removeAt(0)
            position.sameLevelNeighborsShuffled().forEach { neighbor ->
                neighbor.whenRegionlessFloor {
                    size++
                    positionToRegionLookup[neighbor] = region
                    regionTileQueue.add(neighbor)
                }
            }
        }
        return size
    }

    private fun connectRegions(): WorldBuilder {
        for (z in 0 until height - 1) {
            connectRegionsDown(z)
        }
        return this
    }

    private fun connectRegionsDown(z: Int) {
        val connected = mutableListOf<Pair<Position3D, Position3D>>()
        forAllPositionsOnLevel(z) { pos ->
            val abovePos = pos.withRelativeZ(1)
            if (blocks[pos].isEmptyFloor
                    && blocks[abovePos].isEmptyFloor
                    && connected.contains(pos to abovePos).not()) {
                connected.add(pos to abovePos)
            }
        }
        connected.shuffle()
        val (pos, abovePos) = connected.firstOrNull() ?: throw NoSuchElementException("Can't connect levels.")
        blocks[pos] = GameBlockFactory.stairsUp()
        blocks[abovePos] = GameBlockFactory.stairsDown()
    }

    private fun addExitStairs(): WorldBuilder {
        var pos: Position3D
        do {
            pos = Position3D.create((Math.random() * width).toInt(), (Math.random() * width).toInt(), 0)
        } while (blocks[pos].isEmptyFloor.not())
        blocks[pos] = GameBlockFactory.exit()
        return this
    }

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }

    private fun forAllPositionsOnLevel(level: Int, fn: (Position3D) -> Unit) {
        worldSize.to2DSize()
                .fetchPositions()
                .map { Positions.from2DTo3D(it, level) }
                .forEach(fn)
    }

    private fun Position3D.whenRegionlessFloor(fn: (Position3D) -> Unit) {
        if (blocks[this].isWall.not() && positionToRegionLookup[this]?.let { it == 0 } == true) {
            fn(this)
        }
    }

    private fun MutableMap<Position3D, GameBlock>.whenPresent(pos: Position3D, fn: (GameBlock) -> Unit) {
        this[pos]?.let(fn)
    }
}
