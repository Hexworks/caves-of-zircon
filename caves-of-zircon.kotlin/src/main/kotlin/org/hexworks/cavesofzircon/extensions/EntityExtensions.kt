package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.properties.TileProperty

fun Entity.fetchTile(): GameTile {
    return property(TileProperty::class.java).get().tile
}
