package org.hexworks.cavesofzircon.entities

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.attributes.Attribute
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

abstract class BaseEntity(val attributes: Set<Attribute> = setOf(),
                          val components: Set<Component> = setOf()) : Entity {

    override val id = IdentifierFactory.randomIdentifier()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Attribute> property(klass: Class<T>): Maybe<T> {
        return Maybe.ofNullable(attributes.firstOrNull { klass.isInstance(it) } as? T)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Component> component(klass: Class<T>): Maybe<T> {
        return Maybe.ofNullable(components.firstOrNull { klass.isInstance(it) } as? T)
    }

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
