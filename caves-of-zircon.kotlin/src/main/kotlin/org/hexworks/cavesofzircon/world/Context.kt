package org.hexworks.cavesofzircon.world

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen

data class Context(val world: World,
                   val screen: Screen,
                   val input: Input,
                   val player: Entity)
