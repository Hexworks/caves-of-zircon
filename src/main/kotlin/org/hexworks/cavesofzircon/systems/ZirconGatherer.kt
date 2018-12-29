package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.attributes.FungusSpread
import org.hexworks.cavesofzircon.attributes.types.Player
import org.hexworks.cavesofzircon.attributes.types.Zircon
import org.hexworks.cavesofzircon.attributes.types.zirconCounter
import org.hexworks.cavesofzircon.commands.PickItemUp
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext

object ZirconGatherer : BaseSystem<GameContext>(FungusSpread::class) {

    override fun executeCommand(command: Command<out EntityType, GameContext>): Boolean {
        var success = false
        command.whenCommandIs<PickItemUp> { (context, source, position) ->
            val world = context.world
            world.findItemsAt(position).withPresentItems { items ->
                val item = items.last()
                source.whenTypeIs<Player> { player ->
                    if (item.type == Zircon) {
                        player.zirconCounter.zirconCount++
                        world.removeEntity(item)
                        logGameEvent("You've picked up a Zircon!")
                        success = true
                    }
                }
            }
        }
        return success
    }
}