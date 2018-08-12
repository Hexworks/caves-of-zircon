package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.tiles.GameTile
import org.hexworks.cavesofzircon.tiles.StaticTile
import org.hexworks.cavesofzircon.tiles.StaticTile.FLOOR
import org.hexworks.cavesofzircon.tiles.StaticTile.WALL
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.grid.TileGrid


class WorldBuilder(private val tileGrid: TileGrid) {

    private val width = tileGrid.size().xLength
    private val height = tileGrid.size().yLength

    private var tiles: MutableMap<Position, GameTile> = mutableMapOf()

    fun build(): World {
        randomizeTiles().smooth(8)
        return World(tiles.toMap())
    }

    private fun randomizeTiles(): WorldBuilder {
        tileGrid.size().fetchPositions().forEach { pos ->
            tiles[pos] = if (Math.random() < 0.5) FLOOR.tile else WALL.tile
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
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                                    || y + oy >= height)
                                continue

                            if (tiles[Positions.create(x + ox, y + oy)] === StaticTile.FLOOR.tile)
                                floors++
                            else
                                rocks++
                        }
                    }
                    newTiles[Positions.create(x, y)] = if (floors >= rocks) StaticTile.FLOOR.tile else StaticTile.WALL.tile
                }
            }
            tiles = newTiles
        }
        return this
    }
}
