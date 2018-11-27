package ctest.components

import ctest.Context
import ctest.commands.Command
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

abstract class BaseComponent : Component {

    override val id = IdentifierFactory.randomIdentifier()

    override fun respondToEvent(command: Command): Boolean {
        return false
    }

    override fun update(context: Context) {
        // no-op
    }
}
