package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.attributes.Equipment
import org.hexworks.cavesofzircon.attributes.Hunger
import org.hexworks.cavesofzircon.attributes.types.Player

val GameEntity<Player>.hunger: Hunger
    get() = findAttribute()

val GameEntity<Player>.equipment: Equipment
    get() = findAttribute()
