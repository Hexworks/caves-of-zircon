package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.Item
import org.hexworks.cavesofzircon.attributes.types.ItemHolder
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing [source] dropping [item] at [position].
 */
data class DropItem(override val context: GameContext,
                    override val source: GameEntity<ItemHolder>,
                    val item: GameEntity<Item>,
                    val position: Position3D) : GameCommand<EntityType> {

    override fun toString() = "dropping item '$source'."
}
