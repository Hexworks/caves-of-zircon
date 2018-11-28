package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cobalt.logging.api.LoggerFactory

object Diggable : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: Command): Boolean {
        return command.whenTypeIs(Dig::class.java) { cmd ->
            val (context, _, target) = cmd
            logger.info("Digging out $target.")
            context.world.removeEntity(target)
        }
    }
}
