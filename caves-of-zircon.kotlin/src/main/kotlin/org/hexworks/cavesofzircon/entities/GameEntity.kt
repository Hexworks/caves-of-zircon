package org.hexworks.cavesofzircon.entities

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.properties.NameProperty
import org.hexworks.cavesofzircon.properties.Property
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.logging.api.LoggerFactory

class GameEntity(properties: Set<Property> = setOf(),
                 components: Set<Component> = setOf()) : BaseEntity(properties, components) {

    private val eventStack = mutableListOf<Command>()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendCommand(command: Command) {
        logger.info("Receiving command '$command' on entity id=$id, name=${fetchName()}")
        eventStack.add(command)
    }

    override fun executeCommand(command: Command): Boolean {
        logger.info("Executing entity command '$command' on entity id=$id, name=${fetchName()}")
        return components.map {
            it.executeCommand(command)
        }.fold(false, Boolean::or)
    }

    override fun update(context: Context) {
        logger.info("Updating entity id=$id, name=${fetchName()}")
        components.forEach {
            it.update(context)
        }
    }

    private fun fetchName() = property(NameProperty::class.java).orElse(UNKNOWN_NAME)

    companion object {
        private val UNKNOWN_NAME = NameProperty("unknown")
    }
}
