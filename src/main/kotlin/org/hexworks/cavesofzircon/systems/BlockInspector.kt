package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.commands.LookAt
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext

object BlockInspector : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<LookAt> { (context, source, position) ->
        if (source.isPlayer) {
            context.world.withBlockAt(position) { block ->
                block.withTopNonPlayerEntity { entity ->
                    logGameEvent("You see: ${entity.name}.")
                }
            }
        }
        Consumed
    }
}
