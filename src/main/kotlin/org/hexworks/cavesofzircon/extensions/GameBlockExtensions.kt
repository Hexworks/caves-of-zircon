package org.hexworks.cavesofzircon.extensions

import org.hexworks.amethyst.api.EntityType
import org.hexworks.cavesofzircon.blocks.GameBlock

val GameBlock?.isEmptyFloor: Boolean
    get() = this?.isEmptyFloor ?: false

val GameBlock?.isWall: Boolean
    get() = this?.hasWall ?: false

fun GameBlock.withTopNonPlayerEntity(fn: (GameEntity<EntityType>) -> Unit) {
    entities.firstOrNull { it.isPlayer.not() }?.let { entity ->
        fn(entity)
    }
}

