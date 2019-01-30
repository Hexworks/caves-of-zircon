package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.Inventory
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.findAttribute

interface ItemHolder : EntityType

val GameEntity<ItemHolder>.inventory: Inventory
    get() = findAttribute()
