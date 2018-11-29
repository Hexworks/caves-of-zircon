package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cobalt.logging.api.LoggerFactory

object Diggable : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: Command) = command.whenCommandIs<Dig> { cmd ->
        val (context, _, target) = cmd
        logger.info("Digging out target entity: $target.")
        context.world.removeEntity(target)
    }
}
