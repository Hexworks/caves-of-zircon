package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.extensions.whenHasProperty
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.attributes.FungusSpread
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Sizes

object FungusGrower : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun update(context: Context) {
        val world = context.world
        context.entity.whenHasProperty<FungusSpread> { prop ->
            val (spreadcount) = prop
            if (spreadcount < 5 && Math.random() < 0.02) {
                logger.info("Fungus is spreading...")
                val spreadSize = Sizes.create((Math.random() * 11).toInt() - 5, (Math.random() * 11).toInt() - 5)
                world.findEmptyLocation(
                        offset = context.entityPosition
                                .withRelativeX(-spreadSize.width / 2)
                                .withRelativeY(-spreadSize.height / 2),
                        size = Sizes.from2DTo3D(spreadSize)).map { emptyLocation ->
                    world.addEntity(EntityFactory.newFungus(), emptyLocation)
                    prop.spreadcount++
                }
            }
        }

    }
}
