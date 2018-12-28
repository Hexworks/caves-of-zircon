package org.hexworks.cavesofzircon.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.data.impl.Position3D

/**
 * Contains data about how hungry an entity is.
 */
class EntityPosition(initialPosition: Position3D = Position3D.unknown()) : Attribute {
    private val positionProperty = createPropertyFrom(initialPosition)
    var position: Position3D
        get() = positionProperty.value
        set(value) {
            positionProperty.value = value
        }
}
