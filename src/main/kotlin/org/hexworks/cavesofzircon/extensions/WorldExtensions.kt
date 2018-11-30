package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.world.World
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.zircon.api.Positions

fun World.addAtEmptyPosition(entity: Entity): Boolean {
    return findEmptyLocation(offset = Positions.default3DPosition(), size = actualSize()).fold(
            whenEmpty = {
                false
            },
            whenPresent = { location ->
                addEntity(entity, location)
                true
            })

}
