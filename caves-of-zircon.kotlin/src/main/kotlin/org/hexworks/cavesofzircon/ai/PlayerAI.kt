package org.hexworks.cavesofzircon.ai

import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.world.World
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.data.Position

class PlayerAI(private val player: Creature,
               private val world: World) : CreatureAI {

    override fun onEnter(position: Position, block: GameBlock): Boolean {
        return when {
            block.isGround() -> true
            block.isDiggable() -> {
                player.dig(position)
                false
            }
            block.hasCreature() -> {
                world.creature(position).map {
                    player.attack(it)
                }
                false
            }
            else -> {
                throw UnsupportedOperationException("Can't comprehend block: $block")
            }
        }
    }
}
