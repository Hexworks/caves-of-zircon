package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.attributes.Experience
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience
    get() = attribute()

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = attribute()

