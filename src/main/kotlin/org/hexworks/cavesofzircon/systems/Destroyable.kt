package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.commands.Destroy
import org.hexworks.cavesofzircon.events.PlayerDied
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

object Destroyable : BaseSystem<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Destroy> { (context, entity) ->
        val world = context.world
        entity.whenHasInventory { inventory ->
            world.addEntity(
                    entity = EntityFactory.newCorpseFrom(entity.type, inventory.items),
                    position = entity.position)
        }
        world.removeEntity(entity)
        if (entity.isPlayer) {
            Zircon.eventBus.publish(PlayerDied)
        }
    }
}
