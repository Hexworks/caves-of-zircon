package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.builders.GameBlockFactory
import org.hexworks.cavesofzircon.extensions.isEmptyFloor
import org.hexworks.cavesofzircon.extensions.isWall
import org.hexworks.cavesofzircon.extensions.sameLevelNeighbors
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import java.util.*


class WorldBuilder(private val worldSize: Size3D) {

    private val width = worldSize.xLength
    private val height = worldSize.yLength
    private val depth = worldSize.zLength
    private var blocks: MutableMap<Position3D, GameBlock> = mutableMapOf()
    private var regions: MutableMap<Position3D, Int> = mutableMapOf()
    private var nextRegion: Int = 0

    fun makeCaves(): WorldBuilder {
        return randomizeTiles()
                .smooth(8)
                .createRegions()
                .connectRegions()
                .addExitStairs()
    }

    fun build(visibleSize: Size3D, actualSize3D: Size3D): World = World(blocks, visibleSize, actualSize3D)

    private fun randomizeTiles(): WorldBuilder {
        for (x in 0 until width) {
            for (y in 0 until height) {
                for (z in 0 until depth) {
                    blocks[Positions.create3DPosition(x, y, z)] = if (Math.random() < 0.5) {
                        GameBlockFactory.floor()
                    } else GameBlockFactory.wall()
                }
            }
        }
        return this
    }

    private fun smooth(times: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        for (time in 0 until times) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    for (z in 0 until depth) {
                        var floors = 0
                        var rocks = 0
                        for (ox in -1..1) {
                            for (oy in -1..1) {
                                if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) {
                                    continue
                                }
                                blocks[Position3D.create(x + ox, y + oy, z)]?.let {
                                    if (it.isEmptyFloor()) {
                                        floors++
                                    } else rocks++
                                }
                            }
                        }
                        newBlocks[Positions.create3DPosition(x, y, z)] = if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
                    }
                }
            }
            blocks = newBlocks
        }
        return this
    }

    private fun createRegions(): WorldBuilder {
        for (z in 0 until depth) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    val pos = Position3D.create(x, y, z)
                    val block: GameBlock? = blocks.get(pos)
                    if (block.isWall() && regions[pos] == 0) {
                        val size = fillRegion(nextRegion++, x, y, z)
                        if (size < 25) {
                            removeRegion(nextRegion - 1, z)
                        }
                    }
                }
            }
        }
        return this
    }

    private fun removeRegion(region: Int, z: Int) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (regions[Positions.create3DPosition(x, y, z)] == region) {
                    regions[Positions.create3DPosition(x, y, z)] = 0
                    blocks[Positions.create3DPosition(x, y, z)] = GameBlockFactory.wall()
                }
            }
        }
    }

    private fun fillRegion(region: Int, x: Int, y: Int, z: Int): Int {
        var size = 1
        val open = ArrayList<Position3D>()
        open.add(Positions.create3DPosition(x, y, z))
        regions[Positions.create3DPosition(x, y, z)] = region
        while (!open.isEmpty()) {
            val p = open.removeAt(0)
            for (neighbor in p.sameLevelNeighbors()) {
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height)
                    continue
                if (regions[neighbor]!! > 0 || blocks[neighbor].isWall())
                    continue
                size++
                regions[neighbor] = region
                open.add(neighbor)
            }
        }
        return size
    }

    private fun connectRegions(): WorldBuilder {
        for (z in 0 until depth - 1) {
            connectRegionsDown(z)
        }
        return this
    }

    private fun connectRegionsDown(z: Int) {
        val connected = ArrayList<Int>()
        for (x in 0 until width) {
            for (y in 0 until height) {
                val r: Int? = regions[Positions.create3DPosition(x, y, z)]?.times(1000)?.plus(regions[Positions.create3DPosition(x, y, z + 1)]!!)
                if (blocks[Positions.create3DPosition(x, y, z)].isEmptyFloor()
                        && blocks[Positions.create3DPosition(x, y, z + 1)].isEmptyFloor()
                        && !connected.contains(r)) {
                    r?.let { connected.add(it) }
                    val foo = regions[Positions.create3DPosition(x, y, z)]
                    val bar = regions[Positions.create3DPosition(x, y, z + 1)]
                    if (foo != null && bar != null) {
                        connectRegionsDown(z, foo, bar)
                    }
                }
            }
        }
    }

    private fun connectRegionsDown(z: Int, r1: Int, r2: Int) {
        val candidates = findRegionOverlaps(z, r1, r2)
        var stairs = 0
        do {
            val (x, y) = candidates.removeAt(0)
            blocks[Positions.create3DPosition(x, y, z)] = GameBlockFactory.stairsDown()
            blocks[Positions.create3DPosition(x, y, z + 1)] = GameBlockFactory.stairsUp()
            stairs++
        } while (candidates.size / stairs > 250)
    }

    private fun findRegionOverlaps(z: Int, r1: Int, r2: Int): MutableList<Position3D> {
        val candidates = ArrayList<Position3D>()
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (blocks[Positions.create3DPosition(x, y, z)].isEmptyFloor()
                        && blocks[Positions.create3DPosition(x, y, z + 1)].isEmptyFloor()
                        && regions[Positions.create3DPosition(x, y, z)] == r1
                        && regions[Positions.create3DPosition(x, y, z + 1)] == r2) {
                    candidates.add(Positions.create3DPosition(x, y, z))
                }
            }
        }
        candidates.shuffle()
        return candidates
    }

    private fun addExitStairs(): WorldBuilder {
        var x: Int
        var y = -1
        do {
            x = (Math.random() * width).toInt()
            y = (Math.random() * height).toInt()
        } while (blocks[Position3D.create(x, y, 0)].isEmptyFloor().not())
        blocks[Position3D.create(x, y, 0)] = GameBlockFactory.stairsUp()
        return this
    }

}
