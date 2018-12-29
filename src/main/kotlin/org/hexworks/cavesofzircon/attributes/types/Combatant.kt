package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.attributes.Equipment
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute
import org.hexworks.cobalt.datatypes.Maybe

interface Combatant : EntityType

val GameEntity<Combatant>.combatStats: CombatStats
    get() = attribute()

val GameEntity<Combatant>.equipment: Maybe<Equipment>
    get() = attribute(Equipment::class)
