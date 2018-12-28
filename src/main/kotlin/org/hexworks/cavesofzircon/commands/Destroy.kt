package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.EntityType
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * A [Command] representing the destruction of [source].
 */
data class Destroy(override val context: GameContext,
                   override val source: GameEntity<EntityType>) : GameCommand<EntityType> {

    override fun toString() = "trying to destroy $source."
}
