package org.hexworks.cavesofzircon.commands

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.zircon.api.data.impl.Position3D

data class MoveCamera(override val context: Context,
                      override val source: Entity,
                      val oldPosition: Position3D,
                      val newPosition: Position3D) : Command {

    override fun toString() = "Moving camera from $oldPosition to $newPosition."
}
