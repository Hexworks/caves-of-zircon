package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.attributes.EntityMetadata
import org.hexworks.cavesofzircon.attributes.EntityType
import org.hexworks.cavesofzircon.attributes.Attribute
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.extensions.orElseThrow

fun Entity.fetchTile() = property<EntityMetadata>().tile

fun Entity.isPlayer() = property<EntityMetadata>().type == EntityType.PLAYER

fun Entity.occupiesBlock() = property<EntityMetadata>().type.occupiesBlock

inline fun <reified T : Attribute> Entity.property(): T = property(T::class.java).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${T::class.simpleName}'.")
}

inline fun <reified T : Component> Entity.component(): T = component(T::class.java).orElseThrow {
    NoSuchElementException("Entity '$this' has no component with type '${T::class.simpleName}'.")
}

inline fun <reified T : Attribute> Entity.whenHasProperty(crossinline fn: (T) -> Unit) = property(T::class.java).map(fn)

inline fun <reified T : Component> Entity.whenHasComponent(crossinline fn: (T) -> Unit) = component(T::class.java).map(fn)

