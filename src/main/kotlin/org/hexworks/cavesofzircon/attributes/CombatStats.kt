package org.hexworks.cavesofzircon.attributes

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.cobalt.databinding.api.expression.concat
import org.hexworks.cobalt.databinding.api.expression.concatWithConvert
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

/**
 * Represents stats for an entity which are used in combat.
 */
data class CombatStats(val maxHpProperty: Property<Int>,
                       val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),
                       val attackValueProperty: Property<Int>,
                       val defenseValueProperty: Property<Int>) : DisplayableAttribute {

    val maxHp: Int by maxHpProperty.asDelegate()
    val hp: Int by hpProperty.asDelegate()
    val attackValue: Int by attackValueProperty.asDelegate()
    val defenseValue: Int by defenseValueProperty.asDelegate()

    /**
     * Executes [fn] when the entity having this attribute
     * should be destroyed according to its [hp] value.
     */
    fun whenShouldBeDestroyed(fn: () -> Unit) {
        if (hpProperty.value < 1) {
            fn()
        }
    }


    override fun toComponent(width: Int): Component {
        return Components.panel().withSize(width, 5).build().apply {
            val header = Components.header().withText("Combat stats").build()
            val health = Components.label().withSize(width, 1).withPosition(0, 1).build()
            val attack = Components.label().withSize(width, 1).withPosition(0, 2).build()
            val defense = Components.label().withSize(width, 1).withPosition(0, 3).build()

            val healthProp = createPropertyFrom("HP: ")
                    .concatWithConvert(hpProperty).concat("/").concatWithConvert(maxHpProperty)
            val attackProp = createPropertyFrom("Attack: ")
                    .concatWithConvert(attackValueProperty)
            val defenseProp = createPropertyFrom("Defense: ")
                    .concatWithConvert(defenseValueProperty)

            health.textProperty.bind(healthProp)
            attack.textProperty.bind(attackProp)
            defense.textProperty.bind(defenseProp)

            addComponent(header)
            addComponent(health)
            addComponent(attack)
            addComponent(defense)
        }
    }

    companion object {

        /**
         * Creates a new [CombatStats] [Attribute].
         */
        fun create(maxHp: Int, hp: Int = maxHp, attackValue: Int, defenseValue: Int) =
                CombatStats(
                        maxHpProperty = createPropertyFrom(maxHp),
                        hpProperty = createPropertyFrom(hp),
                        attackValueProperty = createPropertyFrom(attackValue),
                        defenseValueProperty = createPropertyFrom(defenseValue))
    }
}
