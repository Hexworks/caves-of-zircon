package org.hexworks.cavesofzircon.attributes

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.expression.concatWithConvert
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

/**
 * Contains the amount of Zircons carried by an entity.
 */
data class ZirconCounter(private val zirconCountProperty: Property<Int> = createPropertyFrom(0)) : DisplayableAttribute {

    var zirconCount: Int by zirconCountProperty.asDelegate()

    override fun toComponent(width: Int): Component {
        val zirconProp = createPropertyFrom("Zircons: ")
                .concatWithConvert(zirconCountProperty) { it.value.toString() }
        return Components.header()
                .withText(zirconProp.value)
                .withSize(width, 1)
                .build().apply {
                    textProperty.bind(zirconProp)
                }
    }
}
