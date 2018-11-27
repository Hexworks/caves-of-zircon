package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cobalt.datatypes.Identifier

interface Component {

    val id: Identifier

    fun update(context: Context)

    fun executeCommand(command: Command): Boolean
}
