package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.EntityType
import org.hexworks.cavesofzircon.attributes.EntityTile
import org.hexworks.cavesofzircon.attributes.ItemIcon
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile

interface Item : EntityType

val GameEntity<Item>.tile: Tile
    get() = attribute<EntityTile>().tile

val GameEntity<Item>.iconTile: GraphicTile
    get() = attribute<ItemIcon>().iconTile
