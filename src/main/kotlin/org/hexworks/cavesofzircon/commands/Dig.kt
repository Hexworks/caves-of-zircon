package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.EntityType
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * A [Command] representing [source] digging out [target].
 */
data class Dig(override val context: GameContext,
               override val source: GameEntity<EntityType>,
               override val target: GameEntity<EntityType>) : EntityAction<EntityType, EntityType> {

    override fun toString() = "digging out $target."
}
