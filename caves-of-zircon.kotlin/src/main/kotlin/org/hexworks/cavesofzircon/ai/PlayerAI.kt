package org.hexworks.cavesofzircon.ai

import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.zircon.api.data.Position
import java.lang.UnsupportedOperationException

class PlayerAI(private val creature: Creature) : CreatureAI {

    override fun onEnter(position: Position, block: GameBlock): Boolean {
        return when {
            block.isGround() -> true
            block.isDiggable() -> {
                creature.dig(position)
                false
            }
            else -> {
                throw UnsupportedOperationException("Can't comprehend block: $block")
            }
        }
    }
}
