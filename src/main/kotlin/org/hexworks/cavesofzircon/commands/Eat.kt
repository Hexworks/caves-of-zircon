package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.Food
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * An [EntityAction] representing [source] eating [target].
 */
data class Eat(override val context: GameContext,
               override val source: GameEntity<EntityType>,
               override val target: GameEntity<Food>) : EntityAction<EntityType, Food> {

    override fun toString() = "$source eats $target."
}
