package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.commands.Attack
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cavesofzircon.commands.TryMovePlayerTo
import org.hexworks.cobalt.logging.api.LoggerFactory

object PlayerMover : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: Command): Boolean {
        return command.whenTypeIs(TryMovePlayerTo::class.java) { cmd ->
            val (context, player, position) = cmd
            val world = context.world
            logger.info("Trying to move entity to $position.")

            val block = world.fetchBlockAt(position).get()
            if (block.isOccupied()) {
                val occupier = block.fetchOccupier()
                val autoActions = listOf(
                        Dig(context, player, occupier),
                        Attack(context, player, occupier)).iterator()
                autoActions.forEach {
                    if (occupier.executeCommand(it)) {
                        return@forEach
                    }
                }
            } else {
                world.moveEntity(player, position)
            }
        }
    }
}
