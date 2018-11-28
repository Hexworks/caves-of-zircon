package org.hexworks.cavesofzircon.properties

import org.hexworks.cavesofzircon.blocks.GameTile

data class EntityMetadata(override val name: String,
                          val type: EntityType,
                          val tile: GameTile) : Property
