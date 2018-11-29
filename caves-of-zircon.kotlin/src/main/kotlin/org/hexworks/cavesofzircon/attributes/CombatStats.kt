package org.hexworks.cavesofzircon.attributes

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.internal.property.DefaultProperty

class CombatStats(val maxHp: Int,
                  var hp: Property<Int> = DefaultProperty(maxHp),
                  val attackValue: Int,
                  val defenseValue: Int) : BaseAttribute() {

    fun shouldDie() = hp.value < 1
}
