package org.hexworks.cavesofzircon.entities

import org.hexworks.cavesofzircon.attributes.Attribute
import org.hexworks.cavesofzircon.attributes.EntityMetadata
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.logging.api.LoggerFactory

class GameEntity(val metadata: EntityMetadata,
                 attributes: Set<Attribute> = setOf(),
                 components: Set<Component> = setOf())
    : BaseEntity(attributes.plus(metadata), components) {

    private val eventStack = mutableListOf<Command>()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendCommand(command: Command) {
        logger.debug("Receiving command '$command' on entity '$this'.")
        eventStack.add(command)
    }

    override fun executeCommand(command: Command): Boolean {
        logger.debug("Executing entity command '$command' on entity $this.")
        return components.map {
            it.executeCommand(command)
        }.fold(false, Boolean::or)
    }

    override fun update(context: Context) {
        logger.debug("Updating entity '$this'.")
        components.forEach {
            it.update(context)
        }
    }

    override fun toString() = metadata.name
}
