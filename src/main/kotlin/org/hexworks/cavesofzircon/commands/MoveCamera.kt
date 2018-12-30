package org.hexworks.cavesofzircon.commands

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * A [GameCommand] representing moving [source]'s camera from
 * [oldPosition] to [newPosition].
 */
data class MoveCamera(override val context: GameContext,
                      override val source: GameEntity<EntityType>,
                      val oldPosition: Position3D,
                      val newPosition: Position3D,
                      val cameraMoveDirection: CameraMoveDirection) : GameCommand<EntityType>
