package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.cavesofzircon.attributes.ItemCombatStats
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.findAttribute

interface Weapon : Item

val GameEntity<Weapon>.attackValue: Int
    get() = findAttribute<ItemCombatStats>().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = findAttribute<ItemCombatStats>().defenseValue
