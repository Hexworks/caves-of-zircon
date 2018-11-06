package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.ai.FungusAI
import org.hexworks.cavesofzircon.ai.PlayerAI
import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.repository.GameTileRepository
import org.hexworks.cavesofzircon.world.World


class CreatureFactory(private val world: World) {

    fun newPlayer(): Creature {
        val player = Creature(
                world = world,
                tile = GameTileRepository.player)
        player.ai = PlayerAI(player, world)
        world.addAtEmptyLocation(player, world.visibleSize())
        return player
    }

    fun newFungus(): Creature {
        val fungus = Creature(
                world = world,
                tile = GameTileRepository.fungus)
        world.addAtEmptyLocation(fungus)
        fungus.ai = FungusAI(fungus, this)
        return fungus
    }
}
