package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.commands.LookAt
import org.hexworks.cavesofzircon.commands.MoveTo
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.tryActionsOn
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.extensions.whenHasBlockAt
import org.hexworks.cavesofzircon.world.GameContext

object Movable : BaseSystem<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<MoveTo> { (context, entity, position) ->
        val world = context.world
        world.whenHasBlockAt(position) { block ->
            if (block.isOccupied) {
                entity.tryActionsOn(context, block.occupier)
            } else {
                world.moveEntity(entity, position)
                entity.executeCommand(LookAt(context, entity, position))
            }
        }
    }
}

