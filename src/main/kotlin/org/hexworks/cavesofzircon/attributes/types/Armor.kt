package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.cavesofzircon.attributes.ItemCombatStats
import org.hexworks.cavesofzircon.extensions.AnyGameEntity
import org.hexworks.cavesofzircon.extensions.attribute
import org.hexworks.cavesofzircon.extensions.hasAttribute
import org.hexworks.cavesofzircon.world.GameContext

interface Armor : Item

val AnyGameEntity.isArmor: Boolean
    get() = hasAttribute<Armor>()

val Entity<Armor, GameContext>.attackValue: Int
    get() = attribute<ItemCombatStats>().attackValue

val Entity<Armor, GameContext>.defenseValue: Int
    get() = attribute<ItemCombatStats>().defenseValue
