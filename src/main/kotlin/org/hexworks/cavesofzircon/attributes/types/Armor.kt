package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.cavesofzircon.attributes.ItemCombatStats
import org.hexworks.cavesofzircon.extensions.findAttribute
import org.hexworks.cavesofzircon.world.GameContext

interface Armor : Item

val Entity<Armor, GameContext>.attackValue: Int
    get() = findAttribute<ItemCombatStats>().attackValue

val Entity<Armor, GameContext>.defenseValue: Int
    get() = findAttribute<ItemCombatStats>().defenseValue
