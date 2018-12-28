package org.hexworks.cavesofzircon.extensions

import org.hexworks.cobalt.datatypes.extensions.ifPresent
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.KeyStroke

fun Input.whenCharacterIs(char: Char, fn: (KeyStroke) -> Unit) {
    asKeyStroke().ifPresent { ks ->
        if (ks.getCharacter() == char) {
            fn(ks)
        }
    }
}
