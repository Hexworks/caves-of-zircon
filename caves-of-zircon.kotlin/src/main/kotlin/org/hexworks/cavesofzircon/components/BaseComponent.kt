package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

abstract class BaseComponent : Component {

    override val id = IdentifierFactory.randomIdentifier()

    override fun executeCommand(command: Command): Boolean {
        return false
    }

    override fun update(context: Context) {
        // no-op
    }
}
