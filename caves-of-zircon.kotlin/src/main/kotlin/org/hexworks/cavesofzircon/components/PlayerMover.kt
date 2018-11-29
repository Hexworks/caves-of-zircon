package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.commands.Attack
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cavesofzircon.commands.TryMoveEntityTo
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.logging.api.LoggerFactory

object PlayerMover : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: Command) = command.whenCommandIs<TryMoveEntityTo> { cmd ->
        val (context, player, position) = cmd
        val world = context.world
        world.fetchBlockAt(position).map { block ->
            if (block.isOccupied()) {
                logger.info("Trying to move entity to $position.")
                val occupier = block.fetchOccupier()
                val autoActions = listOf(
                        Dig(context, player, occupier),
                        Attack(context, player, occupier)).iterator()
                autoActions.forEach { action ->
                    if (occupier.executeCommand(action)) {
                        return@forEach
                    }
                }
            } else {
                world.moveEntity(player, position)
            }
        }
    }
}
