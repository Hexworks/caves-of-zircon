package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.logging.api.LoggerFactory

object Diggable : BaseSystem<GameContext>() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Dig> { (context, _, target) ->
        logger.debug("Digging out ${target.name}.")
        context.world.removeEntity(target)
    }
}