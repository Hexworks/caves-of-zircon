package ctest.components

import ctest.Context
import ctest.commands.Command
import org.hexworks.cobalt.datatypes.Identifier

interface Component {

    val id: Identifier

    fun update(context: Context)

    fun respondToEvent(command: Command): Boolean
}
