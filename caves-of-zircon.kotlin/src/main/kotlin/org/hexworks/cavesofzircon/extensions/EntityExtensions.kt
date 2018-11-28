package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.properties.EntityMetadata
import org.hexworks.cavesofzircon.properties.EntityType

fun Entity.fetchTile(): GameTile {
    return property(EntityMetadata::class.java).tile
}

fun Entity.isPlayer() = property(EntityMetadata::class.java).type == EntityType.PLAYER
