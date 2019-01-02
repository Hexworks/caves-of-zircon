package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.zirconCounter
import org.hexworks.cavesofzircon.commands.MoveDown
import org.hexworks.cavesofzircon.events.PlayerWonTheGame
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

object StairDescender : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>): Response {
        val world = command.context.world
        return command.whenCommandIs<MoveDown> { (_, player, position) ->
            world.withBlockAt(position) { block ->
                when {
                    block.hasStairsDown -> {
                        logGameEvent("You move down one level...")
                        world.moveEntity(player, position.withRelativeZ(-1))
                        world.scrollOneDown()
                    }
                    block.hasExit -> {
                        Zircon.eventBus.publish(PlayerWonTheGame(player.zirconCounter.zirconCount))
                    }
                    else -> logGameEvent("You look down and try to find a trapdoor, but you find nothing.")
                }
            }
            Consumed
        }
    }
}
