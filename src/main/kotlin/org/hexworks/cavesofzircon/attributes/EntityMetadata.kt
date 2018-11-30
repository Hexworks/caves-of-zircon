package org.hexworks.cavesofzircon.attributes

import org.hexworks.cavesofzircon.blocks.GameTile

data class EntityMetadata(override val name: String,
                          val type: EntityType,
                          val tile: GameTile) : Attribute
