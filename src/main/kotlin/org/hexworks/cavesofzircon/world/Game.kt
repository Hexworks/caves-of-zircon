package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.GameConfig
import org.hexworks.cavesofzircon.attributes.types.Player
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.zircon.api.data.impl.Size3D

class Game private constructor(val world: World,
                               val player: GameEntity<Player>,
                               val worldSize: Size3D = world.actualSize(),
                               val visibleSize: Size3D = world.visibleSize()) {

    companion object {

        fun create(player: GameEntity<Player>,
                   world: World,
                   worldSize: Size3D = GameConfig.WORLD_SIZE,
                   visibleSize: Size3D = GameConfig.WORLD_SIZE) = Game(
                world = world,
                player = player,
                worldSize = worldSize,
                visibleSize = visibleSize)
    }
}
