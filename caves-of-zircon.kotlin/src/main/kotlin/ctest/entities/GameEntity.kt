package ctest.entities

import ctest.*
import ctest.components.Component
import ctest.commands.Command
import ctest.properties.Property
import org.hexworks.cobalt.logging.api.LoggerFactory

class GameEntity(properties: List<Property> = listOf(),
                 components: List<Component> = listOf()) : BaseEntity(properties, components) {

    private val eventStack = mutableListOf<Command>()
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun sendCommand(command: Command) {
        logger.info("Receiving command")
        eventStack.add(command)
    }

    override fun executeCommand(command: Command): Boolean {
        return components.map {
            it.respondToEvent(command)
        }.fold(false, Boolean::or)
    }

    override fun update(context: Context) {
        components.forEach {
            it.update(context)
        }
    }
}
