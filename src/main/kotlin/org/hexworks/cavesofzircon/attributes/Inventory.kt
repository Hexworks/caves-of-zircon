package org.hexworks.cavesofzircon.attributes

import org.hexworks.amethyst.api.Attribute
import org.hexworks.cavesofzircon.attributes.types.Item
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe

/**
 * Contains the carried items of an entity.
 */
class Inventory(val size: Int) : Attribute {

    private val currentItems = mutableListOf<GameEntity<Item>>()

    val items: List<GameEntity<Item>>
        get() = currentItems.toList()

    val isEmpty: Boolean
        get() = currentItems.isEmpty()
    val isFull: Boolean
        get() = currentItems.size >= size

    fun findItemBy(id: Identifier): Maybe<GameEntity<Item>> {
        return Maybe.ofNullable(items.firstOrNull { it.id == id })
    }

    /**
     * Tries to add an item to this inventory.
     * @return `true` if successful, `false` if not.
     */
    fun addItem(item: GameEntity<Item>): Boolean {
        return if (isFull.not()) {
            currentItems.add(item)
        } else false
    }

    /**
     * Removes an [Item] from this [Inventory].
     * @return `true` if the item was present, `false` if not
     */
    fun removeItem(entity: GameEntity<Item>): Boolean {
        return currentItems.remove(entity)
    }


}
