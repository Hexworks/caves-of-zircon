package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.commands.Command

inline fun <reified T : Command> Command.whenCommandIs(noinline fn: (T) -> Unit): Boolean {
    return whenCommandIs(T::class.java, fn)
}
