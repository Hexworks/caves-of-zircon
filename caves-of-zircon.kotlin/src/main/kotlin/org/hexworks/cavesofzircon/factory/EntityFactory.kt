package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.components.PlayerInputHandler
import org.hexworks.cavesofzircon.components.PlayerCameraHandler
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.entities.GameEntity
import org.hexworks.cavesofzircon.properties.EntityMetadata
import org.hexworks.cavesofzircon.properties.EntityType.*
import org.hexworks.cavesofzircon.repository.GameTileRepository


object EntityFactory {

    fun newPlayer(): Entity {
        return GameEntity(
                entityType = EntityMetadata(
                        name = "player",
                        type = PLAYER,
                        tile = GameTileRepository.player),
                components = setOf(
                        PlayerInputHandler(),
                        PlayerCameraHandler()),
                properties = setOf())
    }

    fun newFungus(): Entity {
        return GameEntity(
                entityType = EntityMetadata(
                        name = "fungus",
                        type = FUNGUS,
                        tile = GameTileRepository.fungus),
                components = setOf(),
                properties = setOf())
    }

    fun newWall(): Entity {
        return GameEntity(
                entityType = EntityMetadata(
                        name = "wall",
                        type = WALL,
                        tile = GameTileRepository.wall),
                components = setOf(),
                properties = setOf())
    }
}
