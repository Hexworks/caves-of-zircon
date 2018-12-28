package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.attributes.types.zirconCounter
import org.hexworks.cavesofzircon.commands.MoveDown
import org.hexworks.cavesofzircon.commands.MoveUp
import org.hexworks.cavesofzircon.events.PlayerWonTheGame
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

object StairClimber : BaseSystem<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>): Boolean {
        val world = command.context.world
        return command.whenCommandIs<MoveUp> { (_, player, position) ->
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
        } or command.whenCommandIs<MoveDown> { (_, player, position) ->
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
        }
    }
}
