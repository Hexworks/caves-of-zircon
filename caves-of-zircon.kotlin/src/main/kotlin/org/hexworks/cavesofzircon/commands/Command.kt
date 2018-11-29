package org.hexworks.cavesofzircon.commands

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.world.Context

interface Command {

    val context: Context
    val source: Entity

    @Suppress("UNCHECKED_CAST")
    fun <T : Command, U : Any> whenCommandIs(klass: Class<T>,
                                             fn: (T) -> U,
                                             otherwise: () -> U): U {
        return if (klass.isInstance(this)) {
            fn(klass.cast(this))
        } else {
            otherwise()
        }
    }

    fun <T : Command> whenCommandIs(klass: Class<T>,
                                    fn: (T) -> Unit): Boolean {
        return if (klass.isInstance(this)) {
            fn(klass.cast(this))
            true
        } else {
            false
        }
    }
}

