package org.hexworks.cavesofzircon.attributes

import org.hexworks.cavesofzircon.extensions.logGameEvent
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
            val prevValue = textProperty.value
            textProperty.value = calculateHunger()
            if(textProperty.value != prevValue) {
                logGameEvent("You hear a loud growl...oh, it is just your stomach.")
            }
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
            in (maxValue * .75).toInt()..maxValue -> "Full"
            in (maxValue * .5).toInt() until (maxValue * .75).toInt() -> "Stuffed"
            in (maxValue * .25).toInt() until (maxValue * .5).toInt() -> "Hungry"
            else -> {
                "Starving"
            }
        }
        return "Hunger: $txt"
    }
}
