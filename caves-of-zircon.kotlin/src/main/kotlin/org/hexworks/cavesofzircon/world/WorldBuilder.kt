package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.tiles.GameTile
import org.hexworks.cavesofzircon.tiles.GameTiles
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size


class WorldBuilder(private val size: Size) {

    private val width = size.width
    private val height = size.height

    private var tiles: MutableMap<Position, GameTile> = mutableMapOf()


    fun makeCaves(): WorldBuilder {
        randomizeTiles().smooth(8)
        return this
    }

    fun build(): World {
        return World(tiles.toMap(), size)
    }

    private fun randomizeTiles(): WorldBuilder {
        size.fetchPositions().forEach { pos ->
            tiles[pos] = if (Math.random() < 0.5) GameTiles.floor else GameTiles.wall
        }
        return this
    }

    private fun smooth(times: Int): WorldBuilder {
        val newTiles: MutableMap<Position, GameTile> = mutableMapOf()
        for (time in 0 until times) {
            for (x in 0 until width) {
                for (y in 0 until height) {
                    var floors = 0
                    var rocks = 0
                    for (ox in -1..1) {
                        for (oy in -1..1) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height)
                                continue
                            if (tiles[Positions.create(x + ox, y + oy)] === GameTiles.floor)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Positions.create(x, y)] = if (floors >= rocks) GameTiles.floor else GameTiles.wall
                }
            }
            tiles = newTiles
        }
        return this
    }
}
