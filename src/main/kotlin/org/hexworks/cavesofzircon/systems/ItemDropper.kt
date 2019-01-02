package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.inventory
import org.hexworks.cavesofzircon.commands.DropItem
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.world.GameContext

object ItemDropper : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<DropItem> { (context, itemHolder, item, position) ->
        if (itemHolder.inventory.removeItem(item)) {
            context.world.addEntity(item, position)
        }
        Consumed
    }
}
