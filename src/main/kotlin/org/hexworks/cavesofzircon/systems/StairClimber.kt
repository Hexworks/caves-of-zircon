package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.zirconCounter
import org.hexworks.cavesofzircon.commands.MoveUp
import org.hexworks.cavesofzircon.events.PlayerWonTheGame
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.responseWhenCommandIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

object StairClimber : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>): Response {
        val world = command.context.world
        return command.responseWhenCommandIs<MoveUp> { (_, player, position) ->
            world.withBlockAt(position) { block ->
                when {
                    block.hasStairsUp -> {
                        logGameEvent("You move up one level...")
                        world.moveEntity(player, position.withRelativeZ(1))
                        world.scrollOneUp()
                    }
                    block.hasExit -> {
                        Zircon.eventBus.publish(PlayerWonTheGame(player.zirconCounter.zirconCount))
                    }
                    else -> logGameEvent("You jump up and try to reach the ceiling. You fail.")
                }
            }
            Consumed
        }
    }
}
