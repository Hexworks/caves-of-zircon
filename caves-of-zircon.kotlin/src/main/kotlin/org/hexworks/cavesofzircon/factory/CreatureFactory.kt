package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.ai.PlayerAI
import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.repository.GameTileRepository
import org.hexworks.cavesofzircon.world.World
import org.hexworks.zircon.api.kotlin.map


class CreatureFactory(private val world: World) {

    fun newPlayer(): Creature {
        val position = world.findEmptyLocation()
        val player = Creature(
                initialPosition = position.to2DPosition(),
                world = world,
                tile = GameTileRepository.player)
        world.fetchBlockAt(position).map {
            it.setTile(player.tile)
        }
        player.ai = PlayerAI(player)
        return player
    }
}
