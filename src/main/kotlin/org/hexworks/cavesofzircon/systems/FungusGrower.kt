package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.FungusSpread
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.findAttribute
import org.hexworks.cavesofzircon.extensions.position
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.Sizes

object FungusGrower : BaseBehavior<GameContext>(FungusSpread::class) {

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val world = context.world
        val fungusSpread = entity.findAttribute<FungusSpread>()
        val (spreadcount) = fungusSpread
        return if (spreadcount < 4 && Math.random() < 0.015) {
            val spreadSize = Sizes.create(
                    xLength = Math.max(0, (Math.random() * 10).toInt() - 5),
                    yLength = Math.max(0, (Math.random() * 10).toInt() - 5))
            world.findEmptyLocationWithin(
                    offset = entity.position
                            .withRelativeX(-spreadSize.width / 2)
                            .withRelativeY(-spreadSize.height / 2),
                    size = Sizes.from2DTo3D(spreadSize)).map { emptyLocation ->
                world.addEntity(EntityFactory.newFungus(), emptyLocation)
                fungusSpread.spreadcount++
            }
            true
        } else false
    }
}
