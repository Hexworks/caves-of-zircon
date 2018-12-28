package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.commands.MoveTo
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.position
import org.hexworks.cavesofzircon.extensions.sameLevelNeighbors
import org.hexworks.cavesofzircon.world.GameContext

object Wanderer : BaseSystem<GameContext>() {

    override fun update(entity: GameEntity<EntityType>, context: GameContext): Boolean {
        val pos = entity.position
        if (pos.isUnknown().not()) {
            entity.executeCommand(MoveTo(
                    context = context,
                    source = entity,
                    position = pos.sameLevelNeighbors().first()))
        }
        return true
    }
}
