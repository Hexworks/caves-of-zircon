package org.hexworks.cavesofzircon.world

import org.hexworks.amethyst.api.Context
import org.hexworks.cavesofzircon.attributes.types.Player
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen

data class GameContext(val world: World,
                       val screen: Screen,
                       val input: Input,
                       val player: GameEntity<Player>) : Context
