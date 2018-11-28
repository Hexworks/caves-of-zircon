package org.hexworks.cavesofzircon.entities

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.properties.EntityMetadata
import org.hexworks.cavesofzircon.properties.Property
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.logging.api.LoggerFactory

class GameEntity(val metadata: EntityMetadata,
                 properties: Set<Property> = setOf(),
                 components: Set<Component> = setOf())
    : BaseEntity(properties.plus(metadata), components) {

    private val eventStack = mutableListOf<Command>()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendCommand(command: Command) {
        logger.debug("Receiving command '$command' on entity id=$id, name=${metadata.name}")
        eventStack.add(command)
    }

    override fun executeCommand(command: Command): Boolean {
        logger.debug("Executing entity command '$command' on entity id=$id, name=${metadata.name}")
        return components.map {
            it.executeCommand(command)
        }.fold(false, Boolean::or)
    }

    override fun update(context: Context) {
        logger.debug("Updating entity id=$id, name=${metadata.name}")
        components.forEach {
            it.update(context)
        }
    }

    override fun toString() = "Entity(name=${metadata.name}, type=${metadata.type})"
}
