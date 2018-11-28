package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.components.Diggable
import org.hexworks.cavesofzircon.components.PlayerCameraMover
import org.hexworks.cavesofzircon.components.PlayerInputHandler
import org.hexworks.cavesofzircon.components.PlayerMover
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.entities.GameEntity
import org.hexworks.cavesofzircon.properties.EntityMetadata
import org.hexworks.cavesofzircon.properties.EntityType.*
import org.hexworks.cavesofzircon.repository.GameTileRepository


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
                properties = setOf())
    }

    fun newFungus(): Entity {
        return GameEntity(
                metadata = EntityMetadata(
                        name = "fungus",
                        type = FUNGUS,
                        tile = GameTileRepository.fungus),
                components = setOf(),
                properties = setOf())
    }

    fun newWall(): Entity {
        return GameEntity(
                metadata = EntityMetadata(
                        name = "wall",
                        type = WALL,
                        tile = GameTileRepository.wall),
                components = setOf(
                        Diggable),
                properties = setOf())
    }
}
