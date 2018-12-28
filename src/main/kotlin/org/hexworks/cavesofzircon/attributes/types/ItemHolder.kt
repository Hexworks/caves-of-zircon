package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.EntityType
import org.hexworks.cavesofzircon.attributes.Inventory
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute

interface ItemHolder : EntityType

val GameEntity<ItemHolder>.inventory: Inventory
    get() = attribute()
