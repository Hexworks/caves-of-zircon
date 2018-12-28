package org.hexworks.cavesofzircon.attributes

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

/**
 * Contains data about how hungry an entity is.
 */
class Hunger(initialValue: Int,
             val maxValue: Int) : DisplayableAttribute {

    var currentValue: Int
        get() = currentValueProperty.value
        set(value) {
            currentValueProperty.value = Math.min(value, maxValue)
            textProperty.value = calculateHunger()
        }

    private val currentValueProperty = createPropertyFrom(initialValue)
    private val textProperty = createPropertyFrom(calculateHunger())

    override fun toComponent(width: Int): Component {
        return Components.header()
                .withSize(width, 1)
                .withText(textProperty.value)
                .build().apply {
                    textProperty.bind(this@Hunger.textProperty)
                }
    }

    private fun calculateHunger(): String {
        val txt = when (currentValue) {
            1000 -> "Full"
            in 750..999 -> "Stuffed"
            in 500..749 -> "Hungry"
            else -> {
                "Starving"
            }
        }
        return "Hunger: $txt"
    }
}
