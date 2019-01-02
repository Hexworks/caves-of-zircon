package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.inventory
import org.hexworks.cavesofzircon.commands.PickItemUp
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext

object ItemPicker : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<PickItemUp> { (context, itemHolder, position) ->
        val world = context.world
        world.findItemsAt(position).whenHasItems { items ->
            val item = items.last()
            if (itemHolder.inventory.addItem(item)) {
                world.removeEntity(item)
                val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item.")
            }
        }
        Consumed
    }
}
