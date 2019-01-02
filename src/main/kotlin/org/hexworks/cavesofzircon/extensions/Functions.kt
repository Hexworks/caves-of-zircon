package org.hexworks.cavesofzircon.extensions

import org.hexworks.amethyst.api.Entities.newEntityOfType
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.events.GameLogEvent
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon

fun <T : EntityType> newGameEntityOfType(type: T, init: EntityBuilder<T, GameContext>.() -> Unit) =
        newEntityOfType(type, init)

fun logGameEvent(text: String) {
    Zircon.eventBus.publish(GameLogEvent(text))
}
