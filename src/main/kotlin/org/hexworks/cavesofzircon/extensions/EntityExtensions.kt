package org.hexworks.cavesofzircon.extensions

import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.EntityActions
import org.hexworks.cavesofzircon.attributes.EntityPosition
import org.hexworks.cavesofzircon.attributes.EntityTile
import org.hexworks.cavesofzircon.attributes.Inventory
import org.hexworks.cavesofzircon.attributes.flags.BlockOccupier
import org.hexworks.cavesofzircon.attributes.flags.VisionBlocker
import org.hexworks.cavesofzircon.attributes.types.*
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.extensions.orElseThrow
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSuperclassOf

var AnyGameEntity.position
    get() = attribute(EntityPosition::class).orElseThrow {
        IllegalArgumentException("This Entity has no EntityPosition")
    }.position
    set(value) {
        attribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = this.attribute<EntityTile>().tile

val AnyGameEntity.occupiesBlock: Boolean
    get() = hasAttribute<BlockOccupier>()

val AnyGameEntity.blocksVision: Boolean
    get() = hasAttribute<VisionBlocker>()

val AnyGameEntity.isPlayer: Boolean
    get() = this.type == Player

val AnyGameEntity.isWall: Boolean
    get() = this.type == Wall

val AnyGameEntity.isStairsUp: Boolean
    get() = this.type is StairsUp

val AnyGameEntity.isExit: Boolean
    get() = this.type is Exit

val AnyGameEntity.isStairsDown: Boolean
    get() = this.type is StairsDown

inline fun <reified T : Attribute> AnyGameEntity.attribute(): T = attribute(T::class).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${T::class.simpleName}'.")
}

inline fun <reified T : Attribute> AnyGameEntity.hasAttribute() = attribute(T::class).isPresent

inline fun <reified T : Attribute> AnyGameEntity.whenHasAttribute(crossinline fn: (T) -> Unit) = attribute(T::class).map(fn)

@Suppress("UNCHECKED_CAST")
inline fun <reified T : EntityType> Iterable<AnyGameEntity>.filterType(): List<Entity<T, GameContext>> {
    return filter { T::class.isSuperclassOf(it.type::class) }.toList() as List<Entity<T, GameContext>>
}

fun AnyGameEntity.hasPosition() = position.isUnknown().not()

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Attribute> AnyGameEntity.attributesOfType(): List<T> {
    return fetchAttributes().filter { T::class.isSuperclassOf(it::class) }.toList() as List<T>
}

fun AnyGameEntity.tryActionsOn(context: GameContext, target: AnyGameEntity) {
    attribute<EntityActions>()
            .createActionsFor(context, this, target)
            .forEach { action ->
                if (target.executeCommand(action)) {
                    return@forEach
                }
            }
}

fun AnyGameEntity.hasInventory() = hasAttribute<Inventory>()

fun AnyGameEntity.whenHasInventory(fn: (Inventory) -> Unit) {
    whenHasAttribute(fn)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : EntityType> AnyGameEntity.whenTypeIs(fn: (Entity<T, GameContext>) -> Unit) {
    if (this.type::class.isSubclassOf(T::class)) {
        fn(this as Entity<T, GameContext>)
    }
}

