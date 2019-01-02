package org.hexworks.cavesofzircon.extensions

import org.hexworks.amethyst.api.Command
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.world.GameContext

inline fun <reified T : Command<out EntityType, GameContext>> Command<out EntityType, GameContext>.whenCommandIs(
        noinline fn: (T) -> Response): Response {
    return whenCommandIs(T::class, fn)
}
