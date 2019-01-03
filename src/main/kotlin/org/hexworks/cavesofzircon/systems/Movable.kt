package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.commands.LookAt
import org.hexworks.cavesofzircon.commands.MoveTo
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.tryActionsOn
import org.hexworks.cavesofzircon.extensions.responseWhenCommandIs
import org.hexworks.cavesofzircon.extensions.whenHasBlockAt
import org.hexworks.cavesofzircon.world.GameContext

object Movable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.responseWhenCommandIs<MoveTo> { (context, entity, position) ->
        val world = context.world
        var result: Response = Pass
        world.whenHasBlockAt(position) { block ->
            result = Consumed
            if (block.isOccupied) {
                entity.tryActionsOn(context, block.occupier)
            } else {
                world.moveEntity(entity, position)
                entity.executeCommand(LookAt(context, entity, position))
            }
        }
        result
    }
}

