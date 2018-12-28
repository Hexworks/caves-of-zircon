package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.cavesofzircon.attributes.ItemCombatStats
import org.hexworks.cavesofzircon.extensions.AnyGameEntity
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute
import org.hexworks.cavesofzircon.extensions.hasAttribute

interface Weapon : Item

val AnyGameEntity.isWeapon: Boolean
    get() = hasAttribute<Weapon>()

val GameEntity<Weapon>.attackValue: Int
    get() = attribute<ItemCombatStats>().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = attribute<ItemCombatStats>().defenseValue
