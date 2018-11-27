package org.hexworks.cavesofzircon.commands

import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cavesofzircon.entities.Entity

interface Command {

    val context: Context
    val source: Entity

    @Suppress("UNCHECKED_CAST")
    fun <T : Command, U : Any> whenTypeIs(perform: (T) -> U, otherwise: () -> U): U {
        return (this as? T)?.let {
            perform(this)
        } ?: otherwise()
    }
}

