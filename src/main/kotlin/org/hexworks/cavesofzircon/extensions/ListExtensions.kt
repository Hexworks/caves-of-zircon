package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.attributes.types.Item

fun <T : Any> List<T>.whenNotEmpty(fn: (List<T>) -> Unit) {
    if (this.isNotEmpty()) {
        fn(this)
    }
}

fun List<GameEntity<Item>>.withPresentItems(fn: (List<GameEntity<Item>>) -> Unit) {
    whenNotEmpty(fn)
}
