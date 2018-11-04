package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.factory.GameBlockFactory
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size


class WorldBuilder(private val size: Size) {

    private val width = size.width
    private val height = size.height

    private var blocks: MutableMap<Position, GameBlock> = mutableMapOf()


    fun makeCaves(): WorldBuilder {
        randomizeBlocks().smooth(8)
        return this
    }

    fun build(): World {
        return World(blocks.toMap(), size)
    }

    private fun randomizeBlocks(): WorldBuilder {
        size.fetchPositions().forEach { pos ->
            blocks[pos] = if (Math.random() < 0.5) GameBlockFactory.floor() else GameBlockFactory.wall()
        }
        return this
    }

    private fun smooth(times: Int): WorldBuilder {
        val newBlocks: MutableMap<Position, GameBlock> = mutableMapOf()
        for (time in 0 until times) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    var floors = 0
                    var walls = 0
                    for (ox in -1..1) {
                        for (oy in -1..1) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height)
                                continue
                            if (blocks[Positions.create(x + ox, y + oy)] == GameBlockFactory.floor())
                                floors++
                            else
                                walls++
                        }
                    }
                    newBlocks[Positions.create(x, y)] = if (floors >= walls) GameBlockFactory.floor() else GameBlockFactory.wall()
                }
            }
            blocks = newBlocks
        }
        return this
    }
}
