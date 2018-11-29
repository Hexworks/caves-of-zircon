package org.hexworks.cavesofzircon.commands

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.world.Context

data class Dig(override val context: Context,
               override val source: Entity,
               val target: Entity) : Command {

    override fun toString() = "Digging out $target."
}
