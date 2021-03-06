package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * A [GameCommand] representing [source] exerting [force].
 */
data class Exert(override val context: GameContext,
                 override val source: GameEntity<EntityType>,
                 val force: Int) : GameCommand<EntityType>
