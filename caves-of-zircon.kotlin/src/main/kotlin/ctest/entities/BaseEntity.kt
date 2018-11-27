package ctest.entities

import ctest.components.Component
import ctest.Context
import ctest.commands.Command
import ctest.properties.Property
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

abstract class BaseEntity(override val properties: List<Property>,
                          override val components: List<Component>) : Entity {

    override val id = IdentifierFactory.randomIdentifier()

    override fun sendCommand(command: Command) {
        // no-op
    }

    override fun executeCommand(command: Command): Boolean {
        return false
    }

    override fun update(context: Context) {
        // no-op
    }
}
