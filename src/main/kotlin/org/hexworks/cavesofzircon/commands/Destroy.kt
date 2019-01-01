package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * A [GameCommand] representing the destruction of [source].
 */
data class Destroy(override val context: GameContext,
                   override val source: GameEntity<EntityType>,
                   val cause: String = "natural causes.") : GameCommand<EntityType>
