package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.ai.FungusAI
import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.components.PlayerMoveHandler
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.entities.GameEntity
import org.hexworks.cavesofzircon.properties.NameProperty
import org.hexworks.cavesofzircon.properties.TileProperty
import org.hexworks.cavesofzircon.repository.GameTileRepository
import org.hexworks.cavesofzircon.world.World


class CreatureFactory(private val world: World) {

    fun newPlayer(): Entity {
        return GameEntity(
                components = setOf(
                        PlayerMoveHandler()),
                properties = setOf(
                        NameProperty("player"),
                        TileProperty(GameTileRepository.player)))
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
