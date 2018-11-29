package org.hexworks.cavesofzircon.builders

import org.hexworks.cavesofzircon.components.*
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.entities.GameEntity
import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.attributes.EntityMetadata
import org.hexworks.cavesofzircon.attributes.EntityType.*
import org.hexworks.cavesofzircon.attributes.FungusSpread


object EntityFactory {

    fun newPlayer(): Entity {
        return GameEntity(
                metadata = EntityMetadata(
                        name = "player",
                        type = PLAYER,
                        tile = GameTileRepository.player),
                components = setOf(
                        PlayerInputHandler,
                        PlayerCameraMover,
                        PlayerMover),
                attributes = setOf(
                        CombatStats(
                                maxHp = 100,
                                attackValue = 20,
                                defenseValue = 20)))
    }

    fun newFungus(): Entity {
        return GameEntity(
                metadata = EntityMetadata(
                        name = "fungus",
                        type = FUNGUS,
                        tile = GameTileRepository.fungus),
                components = setOf(
                        FungusGrower,
                        Attackable),
                attributes = setOf(
                        FungusSpread(),
                        CombatStats(
                                maxHp = 10,
                                attackValue = 0,
                                defenseValue = 0)))
    }

    fun newWall(): Entity {
        return GameEntity(
                metadata = EntityMetadata(
                        name = "wall",
                        type = WALL,
                        tile = GameTileRepository.wall),
                components = setOf(
                        Diggable),
                attributes = setOf())
    }
}
