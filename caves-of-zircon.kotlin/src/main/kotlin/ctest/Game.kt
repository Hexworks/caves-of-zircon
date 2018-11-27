package ctest

import ctest.entities.GameEntity
import org.hexworks.zircon.api.Positions

class Game {

    private val world = World()

    init {
        val player = GameEntity()
        world.addEntity(player, Positions.create3DPosition(1, 1, 1))
    }
}
