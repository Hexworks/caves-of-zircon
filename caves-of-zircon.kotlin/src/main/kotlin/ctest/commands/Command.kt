package ctest.commands

import ctest.Context
import ctest.entities.Entity

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

