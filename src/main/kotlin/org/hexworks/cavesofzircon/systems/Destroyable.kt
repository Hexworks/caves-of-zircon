package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.commands.Destroy
import org.hexworks.cavesofzircon.events.PlayerDied
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

object Destroyable : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Destroy> { (context, entity, cause) ->
        val world = context.world
        entity.whenHasInventory { inventory ->
            world.addEntity(
                    entity = EntityFactory.newCorpseFrom(entity.type, inventory.items),
                    position = entity.position)
        }
        world.removeEntity(entity)
        if (entity.isPlayer) {
            Zircon.eventBus.publish(PlayerDied("You died because of $cause"))
        }
        Consumed
    }
}
